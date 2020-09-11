package com.devil.sell.controller;

import com.devil.sell.dto.OrderDTO;
import com.devil.sell.enums.ResultEnum;
import com.devil.sell.exception.SellException;
import com.devil.sell.service.IOrderService;
import com.devil.sell.service.IPayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author Hanyanjiao
 * @date 2020/6/15
 */

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IPayService payService;


    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map) {
        //查询订单
        OrderDTO dto = orderService.findOne(orderId);

        if(dto == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //发起支付
        PayResponse response = payService.create(dto);

        map.put("payResponse",response);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create");
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);

        //告诉微信不要再通知了
        return new ModelAndView("pay/success");
    }

}
