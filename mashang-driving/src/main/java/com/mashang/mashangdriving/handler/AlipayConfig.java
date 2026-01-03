package com.mashang.mashangdriving.handler;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    // 应用ID,您的APPID，沙盒环境从支付宝开放平台获取
    private String appId;

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String privateKey;

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String publicKey;

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可访问
    private String notifyUrl;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可访问
    private String returnUrl;

    // 签名方式
    private String signType = "RSA2";

    // 字符编码格式
    private String charset = "utf-8";

    // 支付宝网关，沙盒环境为：https://openapi.alipaydev.com/gateway.do
    private String gatewayUrl;

    // getter和setter省略
}
