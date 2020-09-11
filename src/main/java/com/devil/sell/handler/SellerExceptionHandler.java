package com.devil.sell.handler;

import com.devil.sell.config.ProjectUrlConfig;
import com.devil.sell.exception.SellException;
import com.devil.sell.exception.SellerAuthorizeException;
import com.devil.sell.utils.ResultVoUtil;
import com.devil.sell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Hanyanjiao
 * @date 2020/6/30
 */

@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig config;

    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException(){
        return new ModelAndView("redirect:".concat(config.getSell())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(config.getSell())
                .concat("/sell/wechat/login"));
    }

    @ResponseBody
    @ExceptionHandler(value = SellException.class)
    public ResultVo handlerSellException(SellException e){
        return ResultVoUtil.error(e.getCode(),e.getMessage());
    }
}