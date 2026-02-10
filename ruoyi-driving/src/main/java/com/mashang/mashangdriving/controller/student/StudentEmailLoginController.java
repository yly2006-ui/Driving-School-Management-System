package com.mashang.mashangdriving.controller.student;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Api(tags = "学员登录管理")
@RestController
@RequestMapping("/student")
public class StudentEmailLoginController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    // 从配置文件读取发件人邮箱（和spring.mail.username一致）
    @Value("${spring.mail.username}")
    private String FROM_EMAIL;

    // 1. 发送验证码接口（规避风控版）
    @ApiOperation("发送验证码")
    @PostMapping("/sendEmailCode")
    public AjaxResult sendEmailCode(@RequestParam String email) {
        // 1. 校验邮箱不能为空
        if (StringUtils.isEmpty(email)) {
            return AjaxResult.error("邮箱不能为空");
        }

        // 2. 防刷：1分钟内只能发送一次
        String sendLockKey = "email:send:lock:" + email;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(sendLockKey))) {
            return AjaxResult.error("验证码发送过于频繁，请1分钟后再试");
        }

        // 3. 生成6位随机验证码（纯数字，避免特殊字符风控）
        String code = String.format("%06d", new Random().nextInt(999999));

        // 4. 验证码存入Redis，5分钟过期
        String codeKey = "email:login:code:" + email;
        redisTemplate.opsForValue().set(codeKey, code, 5, TimeUnit.MINUTES);
        // 5. 设置发送锁，1分钟过期
        redisTemplate.opsForValue().set(sendLockKey, "1", 1, TimeUnit.MINUTES);

        // 6. 发送验证码邮件（纯文本+中性词汇，规避风控）
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // 显式指定UTF-8编码，避免乱码
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            // 发件人：邮箱+中性显示名
            helper.setFrom(FROM_EMAIL, "验证码通知");
            // 收件人：学员邮箱
            helper.setTo(email);
            // 邮件标题（简化，避开敏感词）
            helper.setSubject("您的验证码通知");
            // 邮件内容（纯文本+中性表述，无HTML标签）
            String emailContent = "您的验证码是：" + code + "，5分钟内有效，请妥善保管。";
            helper.setText(emailContent, false); // false表示纯文本，不使用HTML

            // 发送邮件
            mailSender.send(message);

            return AjaxResult.success("验证码已发送至您的邮箱，请查收");
        } catch (MessagingException e) {
            // 发送失败，清理Redis缓存
            redisTemplate.delete(codeKey);
            redisTemplate.delete(sendLockKey);
            return AjaxResult.error("验证码发送失败：" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // 2. 邮箱验证码登录接口
    @ApiOperation("邮箱验证码登录")
    @PostMapping("/emailLogin")
    public AjaxResult emailLogin(@RequestParam String email, @RequestParam String code) {
        // 1. 校验参数
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
            return AjaxResult.error("邮箱和验证码不能为空");
        }

        // 2. 校验验证码
        String codeKey = "email:login:code:" + email;
        String cacheCode = redisTemplate.opsForValue().get(codeKey);
        if (cacheCode == null) {
            return AjaxResult.error("验证码已过期，请重新获取");
        }
        if (!cacheCode.equals(code.trim())) { // 兼容前端可能的空格
            return AjaxResult.error("验证码错误，请核对后重试");
        }

        // 3. 查询用户信息
        SysUser user = userService.selectUserByEmail(email);
        if (user == null) {
            return AjaxResult.error("该邮箱未注册，请先注册");
        }

        // 4. 校验用户状态（是否禁用/删除）
//        if ("1".equals(user.getDelFlag())) {
//            return AjaxResult.error("该用户已被删除，无法登录");
//        }
        if ("1".equals(user.getStatus())) {
            return AjaxResult.error("该用户已被禁用，请联系管理员");
        }

        // 5. 生成若依Token
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUser(user);
        String token = tokenService.createToken(loginUser);

        // 6. 验证成功，删除验证码（防止重复使用）
        redisTemplate.delete(codeKey);

        // 7. 返回登录结果
        return AjaxResult.success()
                .put("token", token)
                .put("msg", "登录成功");
    }
}