package com.devil.sell.service.impl;

import com.devil.sell.SellApplicationTests;
import com.devil.sell.dto.OrderDTO;
import com.devil.sell.enums.OrderStatusEnum;
import com.devil.sell.enums.PayStatusEnum;
import com.devil.sell.po.OrderDetail;
import com.devil.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class OrderServiceImplTest extends SellApplicationTests {

    @Autowired
    private IOrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("内蒙古大草原");
        orderDTO.setBuyerName("小妖");
        orderDTO.setBuyerOpenid("123123");
        orderDTO.setBuyerPhone("12345678912");

        List<OrderDetail> orderDetails = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("12341244");
        orderDetail.setProductQuantity(1);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("87654321");
        orderDetail1.setProductQuantity(2);

        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);
        orderDTO.setOrderDetails(orderDetails);

        OrderDTO result = orderService.create(orderDTO);

        log.info("result is {}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO dto = orderService.findOne("1591868338111231966");
        log.info("dto is {}",dto);
        Assert.assertNotNull(dto);
    }

    @Test
    public void findList() {
        Pageable request = PageRequest.of(0,4);
        Page<OrderDTO> result = orderService.findList("123123", request);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO dto = orderService.findOne("1591868338111231966");
        OrderDTO result = orderService.cancel(dto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO dto = orderService.findOne("1591868338111231966");
        OrderDTO result = orderService.finish(dto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO dto = orderService.findOne("1591867670652203494");
        OrderDTO result = orderService.paid(dto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void findAll(){
        Pageable request = PageRequest.of(0,4);
        Page<OrderDTO> orderDTOS = orderService.findAll(request);
//        Assert.assertNotEquals(0,orderDTOS.getTotalElements());
        Assert.assertTrue("查询所有订单",orderDTOS.getTotalElements()>0);
    }
}