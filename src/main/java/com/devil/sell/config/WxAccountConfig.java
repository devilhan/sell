package com.devil.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Hanyanjiao
 * @date 2020/5/14
 */

@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WxAccountConfig {

    //公众号Id
    private String appId;

    private String miniAppId;

    private String appAppId;

    private String appSecret;

    /**
     * 开放平台id
     */
    private String openAppId;

    private String openAppSecret;

    /**
     * 公众平台id
     */
    private String mpAppId;

    private String mpAppSecret;

    //商户号
    private String mchId;

    //商户秘钥
    private String mchKey;

    private String keyPath;

    private String notifyUrl;

    private String returnUrl;

    private Map<String,String> templateId;
}
