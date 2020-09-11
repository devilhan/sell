package com.devil.sell.service.impl;

import com.devil.sell.dto.OrderDTO;
import com.devil.sell.service.IOrderService;
import com.devil.sell.service.IPushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class PushMessageServiceImplTest extends SellerServiceImplTest {

    @Autowired
    private IPushMessageService pushMessageService;

    @Autowired
    private IOrderService orderService;

    @Test
    public void orderStatus() {
        OrderDTO dto = orderService.findOne("1592898537880441746");
        pushMessageService.orderStatus(dto);
    }
}