package com.mashang.mashangdriving.handler;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlipayUtils {

    @Autowired
    private AlipayConfig alipayConfig;

    private static AlipayClient alipayClient;

    @PostConstruct
    public void init() {
        alipayClient = new DefaultAlipayClient(
                alipayConfig.getGatewayUrl(),
                alipayConfig.getAppId(),
                alipayConfig.getPrivateKey(),
                "json",
                alipayConfig.getCharset(),
                alipayConfig.getPublicKey(),
                alipayConfig.getSignType()
        );
    }

    /**
     * 生成支付表单HTML
     */
    public String createPayForm(String outTradeNo, String totalAmount, String subject, String body, String timeoutExpress) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(alipayConfig.getReturnUrl());
        request.setNotifyUrl(alipayConfig.getNotifyUrl());

        // 组装请求参数，支持更多业务字段
        String bizContent = "{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"," +
                "\"total_amount\":\"" + totalAmount + "\"," +
                "\"subject\":\"" + subject + "\"," +
                "\"body\":\"" + (body != null ? body : "") + "\"," +
                "\"timeout_express\":\"" + (timeoutExpress != null ? timeoutExpress : "15m") + "\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"" +
                "}";
        request.setBizContent(bizContent);

        // 执行请求获取支付表单
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        String result = response.getBody();
        System.out.println("支付宝支付表单响应：" + result);
        return result;
    }

    /**
     * 查询订单支付状态
     */
    public AlipayTradeQueryResponse queryOrder(Long billId) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"bill_id\":\"" + billId + "\"" +
                "}");
        return alipayClient.execute(request);
    }

    /**
     * 生成支付宝支付表单HTML
     * @param orderNumber 商户订单号（out_trade_no）
     * @param totalAmount 订单总金额（单位：元，精确到小数点后两位）
     * @param subject 订单标题
     * @return 支付表单HTML字符串
     * @throws AlipayApiException 支付宝接口调用异常
     */
    public String createPayForm(String orderNumber, String totalAmount, String subject) throws AlipayApiException {
        // 创建支付请求对象
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 设置同步回调地址（从配置中获取）
        request.setReturnUrl(alipayConfig.getReturnUrl());
        // 设置异步通知地址（从配置中获取）
        request.setNotifyUrl(alipayConfig.getNotifyUrl());

        // 组装业务参数
        String bizContent = "{" +
                "\"out_trade_no\":\"" + orderNumber + "\"," +  // 商户订单号
                "\"total_amount\":\"" + totalAmount + "\"," +   // 订单总金额
                "\"subject\":\"" + subject + "\"," +           // 订单标题
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"" + // 产品码（固定值）
                "}";
        request.setBizContent(bizContent);

        // 调用支付宝接口生成支付表单
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        if (!response.isSuccess()) {
            throw new AlipayApiException("创建支付表单失败：" + response.getMsg());
        }

        // 返回支付表单HTML
        return response.getBody();
    }
}
