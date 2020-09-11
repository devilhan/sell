package com.devil.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * @author Hanyanjiao
 * @date 2020/6/29
 */

@Data
@Component
@ConfigurationProperties(prefix = "project-url")
public class ProjectUrlConfig {

    //公众平台授权
    public String wechatMpAuthorize;

    //开放平台授权
    public String wechatOpenAuthorize;

    //系统连接
    public String sell;
}
