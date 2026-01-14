package com.mashang.mashangdriving.controller.student;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mashang.mashangdriving.domain.entity.DrivingBillRecord;
import com.mashang.mashangdriving.domain.entity.DrivingChargeLtem;
import com.mashang.mashangdriving.domain.entity.DrivingPay;
import com.mashang.mashangdriving.domain.param.manager.query.PayRequest;
import com.mashang.mashangdriving.domain.vo.student.BillRecordDtlVo;
import com.mashang.mashangdriving.domain.vo.student.BillRecordListVo;
import com.mashang.mashangdriving.handler.AlipayTemplate;
import com.mashang.mashangdriving.service.manager.IBillRecordService;
import com.mashang.mashangdriving.service.manager.IDrivingBillRecordService;
import com.mashang.mashangdriving.service.student.IDrivingChargeLtemService;
import com.mashang.mashangdriving.service.student.IPayService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.alipay.api.internal.util.AlipaySignature;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;



@Api(tags = "学员端--财务管理")
@RestController
@RequestMapping("/financial/management")
public class FinancialManagementController extends BaseController {

    @Autowired
    private IDrivingChargeLtemService chargeLtemService;

    @Autowired
    private IPayService payService;

    @Autowired
    private AlipayTemplate alipayTemplate;

    @Autowired
    private IDrivingBillRecordService billRecordService;

    @Autowired
    private IBillRecordService billRecordServiceImpl;

    @PostMapping(value = "/pay", produces = "text/html")
    @ApiOperation("支付接口")
    public String pay(@RequestBody PayRequest req)
            throws AlipayApiException {

        Long chargeItemId;
        String subject;
        double amount;

        // ================== ① 固定收费项目 ==================
        if (req.getChargeItemId() != null) {

            DrivingChargeLtem item =
                    chargeLtemService.getById(req.getChargeItemId());

            if (item == null) {
                throw new RuntimeException("收费项目不存在");
            }

            chargeItemId = item.getChargeLtemId();
            subject = item.getChargeLtemName();
            amount = item.getAmount();

        }
        // ================== ② 自定义收费项目 ==================
        else {

            if (req.getAmount() == null || req.getAmount() <= 0) {
                throw new RuntimeException("自定义金额非法");
            }

            // 新增一个收费项目（不破坏结构）
            DrivingChargeLtem customItem = new DrivingChargeLtem();
            customItem.setChargeLtemName(req.getChargeItemName());
            customItem.setAmount(req.getAmount());
            customItem.setDelFlag("0");

            chargeLtemService.save(customItem);

            chargeItemId = customItem.getChargeLtemId();
            subject = customItem.getChargeLtemName();
            amount = customItem.getAmount();
        }

        // ================== ③ 生成支付记录 ==================
        DrivingPay pay = new DrivingPay();
        pay.setUserId(SecurityUtils.getUserId());
        pay.setChargeLtemId(chargeItemId);
        pay.setDetailedExplanation(req.getDetailedExplanation());
        pay.setPayType("1");
        pay.setCreateTime(new Date());
        pay.setDelFlag("0");
        pay.setStatus("0");

        String payNo = "PAY" + System.currentTimeMillis();
        pay.setPayNo(payNo);

        payService.save(pay);

        // ================== ④ 调支付宝 ==================
        return alipayTemplate.pay(payNo, amount, subject);
    }


    @PostMapping("/notify")
    @ApiOperation("支付宝回调")
    public String notify(HttpServletRequest request) throws Exception {
        // 1. 获取支付宝回调参数
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();

        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = String.join(",", values);
            params.put(name, valueStr);
        }

        // 打印参数，确认不是空
        System.out.println("支付宝回调参数: " + params);

        if (params.isEmpty()) {
            return "fail"; // 没收到参数，直接返回
        }

        // 2. 验签
        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAovxBFYEbZmihGx3erIh9C4IIS4wdspvd+oMuHAvoTGAwHUPlH/vGpkv2JCMiv7MoaQ+PkPjz9TefGVYmR0Ud/o/jVx113m6YU4++uJd8TWe+Zs4BZV5UO048FGrEZQn4RIdWh3QO0AqTY/vJ09JQMZqfk8Zm988rZw52Wvoz6JGoz7wlAc0LTPe3PfHg5r4w5Cx7oSrxSshUeIh7aT/Hq3ElrgKolqdGpIZC7XJoNEWtrSZ194J1DLzSTsdzpSX1Mz3c0EInJkzAngM/Oausz+FE1TeJMNZGkbEttjmUKEFow8G9bc31KbmVWnZtHiRHyd8IioRgRCXrVo+pXACRFQIDAQAB", // 公钥必须是支付宝官方提供的完整公钥字符串
                "utf-8",
                "RSA2"
        );

        if (!signVerified) {
            System.out.println("验签失败");
            return "fail";
        }

        // 3. 业务处理
        String payNo = params.get("out_trade_no");
        DrivingPay pay = payService.getOne(
                new LambdaQueryWrapper<DrivingPay>().eq(DrivingPay::getPayNo, payNo)
        );

        if (pay == null) {
            return "fail";
        }

        DrivingBillRecord record = new DrivingBillRecord();
        record.setPayId(pay.getPayId());
        record.setUserId(pay.getUserId());
        record.setChargeLtemId(pay.getChargeLtemId());
        record.setCreateTime(new Date());
        record.setDelFlag("0");

        billRecordService.save(record);

        return "success";
    }

    @ApiOperation("缴费记录")
    @GetMapping("/payment/record")
    public R<BillRecordListVo> paymentRecord() {

        return R.ok(billRecordServiceImpl.billRecordListVo());
    }

    @ApiOperation("缴费详情")
    @GetMapping("/payment/{payId}")
    @ApiImplicitParam(name = "payId",value = "支付id")
    public R<BillRecordDtlVo> paymentDtl(@PathVariable Long payId) {

        return R.ok(billRecordServiceImpl.paymentDtl(payId));
    }







}
