package com.devil.sell.service;

import com.devil.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * @author Hanyanjiao
 * @date 2020/6/15
 */
public interface IPayService {

    /**
     * 创建订单
     * @param dto
     * @return
     */
    PayResponse create(OrderDTO dto);

    /**
     * 付款后异步通知
     * @param notifyData
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 退款
     * @param dto
     */
    RefundResponse refund(OrderDTO dto);
}
