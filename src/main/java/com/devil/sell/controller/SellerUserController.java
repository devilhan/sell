package com.devil.sell.controller;

import com.devil.sell.config.ProjectUrlConfig;
import com.devil.sell.constant.CookieConstant;
import com.devil.sell.constant.RedisConstant;
import com.devil.sell.enums.ResultEnum;
import com.devil.sell.po.SellerInfo;
import com.devil.sell.service.ISellerService;
import com.devil.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Hanyanjiao
 * @date 2020/6/29
 */

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private ISellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig urlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid")String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){

        //openid是否存在
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);

        if(sellerInfo == null){
            map.put("msg",ResultEnum.LOGIN_FAIL.getMessage());
            map.put("list","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        //设置token值redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire,TimeUnit.SECONDS);

        //设置token至cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,expire);

        return new ModelAndView("redirect:"+urlConfig.getSell()+"/sell/seller/order/list");
    }


    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null){
            //清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }
}
