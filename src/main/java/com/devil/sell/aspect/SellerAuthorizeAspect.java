package com.devil.sell.aspect;

import com.devil.sell.constant.CookieConstant;
import com.devil.sell.constant.RedisConstant;
import com.devil.sell.exception.SellException;
import com.devil.sell.exception.SellerAuthorizeException;
import com.devil.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Hanyanjiao
 * @date 2020/6/30
 */

@Slf4j
@Aspect
@Component
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.devil.sell.controller.Seller*.*(..))" +
            "&& !execution(public * com.devil.sell.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

        if(cookie == null){
            log.warn("【登录校验】 cookie 中查不到token");
            throw new SellerAuthorizeException();
        }

        //去redis里查询
        String token = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

        if(StringUtils.isEmpty(token)){
            log.warn("【登录校验】 redis 中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
