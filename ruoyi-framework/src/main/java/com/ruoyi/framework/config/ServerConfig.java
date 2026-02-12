package com.ruoyi.framework.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.ServletUtils;

/**
 * 服务相关配置
 * 
 * @author ruoyi
 */
@Component
public class ServerConfig
{
    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     * 
     * @return 服务地址
     */
    public String getUrl()
    {
        return "http://mashang.eicp.vip:5555/ms_stu_pro339";
    }

    public static String getDomain(HttpServletRequest request)
    {
        return "http://mashang.eicp.vip:5555/ms_stu_pro339";
    }
}
