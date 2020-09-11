package com.devil.sell.dto;

import com.devil.sell.enums.OrderStatusEnum;
import com.devil.sell.enums.PayStatusEnum;
import com.devil.sell.po.OrderDetail;
import com.devil.sell.utils.EnumUtil;
import com.devil.sell.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/6/11
 */

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String orderId;

    //买家名称
    private String buyerName;

    //买家电话
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信openId
    private String buyerOpenid;

    //订购总金额
    private BigDecimal orderAmount;

    //订单状态 -- 默认为新的订单
    private Integer orderStatus;

    //支付状态 -- 默认为未支付
    private Integer payStatus;

    //创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    //更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    //订单详情
    private List<OrderDetail> orderDetails;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
