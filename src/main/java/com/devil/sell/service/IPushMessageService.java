package com.devil.sell.service;

import com.devil.sell.dto.OrderDTO;

/**
 * 消息推送
 * @author Hanyanjiao
 * @date 2020/6/30
 */
public interface IPushMessageService {

    //订单状态变更消息
    void orderStatus(OrderDTO dto);
}
