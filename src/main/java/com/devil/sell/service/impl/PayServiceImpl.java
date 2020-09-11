package com.devil.sell.service.impl;

import com.devil.sell.dto.OrderDTO;
import com.devil.sell.enums.ResultEnum;
import com.devil.sell.exception.SellException;
import com.devil.sell.service.IOrderService;
import com.devil.sell.service.IPayService;
import com.devil.sell.utils.JsonUtil;
import com.devil.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Hanyanjiao
 * @date 2020/6/15
 */

@Slf4j
@Service
public class PayServiceImpl implements IPayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private IOrderService orderService;

    public static final String ORDER_NAME = "微信点餐订单";
    @Override
    public PayResponse create(OrderDTO dto) {
        PayRequest payRequest = new PayRequest();

        payRequest.setOpenid(dto.getBuyerOpenid());
        payRequest.setOrderAmount(dto.getOrderAmount().doubleValue());
        payRequest.setOrderId(dto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);

        log.info("【微信支付】 发起支付，request={}",JsonUtil.toJson(payRequest));
        PayResponse response = bestPayService.pay(payRequest);
        log.info("【微信支付】 发起支付，response={}",JsonUtil.toJson(response));

        return response;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //安全验证

        //验证签名

        //支付状态


        PayResponse response = bestPayService.asyncNotify(notifyData);

        log.info("【微信支付】 异步通知，response={}",JsonUtil.toJson(response));

        OrderDTO dto = orderService.findOne(response.getOrderId());

        //判断订单是否存在
        if (dto == null){
            log.error("【微信支付】 异步通知，订单不存在 ，orderId={}",response.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //支付金额
        if(!MathUtil.equals(response.getOrderAmount(),dto.getOrderAmount().doubleValue())){
            log.error("【微信支付】 异步通知，订单金额不一致，orderId={},微信支付金额={}，系统金额={}",
                    response.getOrderId(),response.getOrderAmount(),dto.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //支付人(下单人 == 支付人)

        //修改订单支付状态
        orderService.paid(dto);
        return response;
    }

    @Override
    public RefundResponse refund(OrderDTO dto) {
        RefundRequest request = new RefundRequest();
        request.setOrderId(dto.getOrderId());
        request.setOrderAmount(dto.getOrderAmount().doubleValue());
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        log.info("【微信退款】 request={}",JsonUtil.toJson(request));
        RefundResponse refund = bestPayService.refund(request);
        log.info("【微信退款】 response={}",JsonUtil.toJson(refund));
        return refund;
    }
}
