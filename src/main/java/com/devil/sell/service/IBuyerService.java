package com.devil.sell.service;

import com.devil.sell.dto.OrderDTO;

/**
 * @author Hanyanjiao
 * @date 2020/6/12
 */
public interface IBuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);

}
