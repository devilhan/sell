package com.devil.sell.service.impl;

import com.devil.sell.SellApplicationTests;
import com.devil.sell.dto.OrderDTO;
import com.devil.sell.service.IOrderService;
import com.devil.sell.service.IPayService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class PayServiceImplTest extends SellApplicationTests {

    @Autowired
    private IPayService payService;

    @Autowired
    private IOrderService orderService;
    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("1591945432276215276");
        payService.create(orderDTO);
    }

    @Test
    public void refund(){
        OrderDTO orderDTO = orderService.findOne("1591867670652203494");
        payService.refund(orderDTO);
    }
}