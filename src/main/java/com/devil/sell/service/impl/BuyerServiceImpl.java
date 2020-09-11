package com.devil.sell.service.impl;

import com.devil.sell.dto.OrderDTO;
import com.devil.sell.enums.ResultEnum;
import com.devil.sell.exception.SellException;
import com.devil.sell.service.IBuyerService;
import com.devil.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hanyanjiao
 * @date 2020/6/12
 */

@Slf4j
@Service
public class BuyerServiceImpl implements IBuyerService {

    @Autowired
    private IOrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO dto = checkOrderOwner(openid, orderId);
        if(dto == null){
            log.error("【取消订单】 查不到该订单，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(dto);
    }

    private OrderDTO checkOrderOwner(String openid,String orderId){
        OrderDTO dto = orderService.findOne(orderId);
        if (orderId == null){
            return null;
        }
        if(!dto.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】 订单的openid不一致 ，openid={},orderDTO={}",openid,dto);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return dto;
    }
}
