package com.devil.sell.po;

import com.devil.sell.enums.OrderStatusEnum;
import com.devil.sell.enums.PayStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单总信息
 * @author Hanyanjiao
 * @date 2020/6/11
 */

@Data
@Entity
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class OrderMaster {

    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    //支付状态 -- 默认为未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}
