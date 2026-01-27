package com.mashang.mashangdriving.handler;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
@Component
@Data
public class AlipayTemplate {

    // ======== 基础配置 ========

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.merchantPrivateKey}")
    private String merchantPrivateKey;

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Value("${alipay.signType}")
    private String signType;

    @Value("${alipay.charset}")
    private String charset;

    @Value("${alipay.gatewayUrl}")
    private String gatewayUrl;

    // 订单超时时间
    private String timeout = "1m";

    /**
     * 发起支付宝网页支付
     *
     * @param payNo   商户支付号（DrivingPay.payNo）
     * @param amount  支付金额
     * @param subject 支付项目名称（收费项目名 / 自定义名）
     */
    public String pay(String payNo, Double amount, String subject)
            throws AlipayApiException {

        // 1️⃣ 创建支付宝客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                gatewayUrl,
                appId,
                merchantPrivateKey,
                "json",
                charset,
                alipayPublicKey,
                signType
        );

        // 2️⃣ 创建支付请求
        AlipayTradePagePayRequest request =
                new AlipayTradePagePayRequest();

        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);

        // 3️⃣ 业务参数
        request.setBizContent("{"
                + "\"out_trade_no\":\"" + payNo + "\","
                + "\"total_amount\":\"" + amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"timeout_express\":\"" + timeout + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\""
                + "}");

        // 4️⃣ 返回支付宝收银台页面
        return alipayClient.pageExecute(request).getBody();
    }
}
