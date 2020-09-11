package com.devil.sell.dao;

import com.devil.sell.SellApplicationTests;
import com.devil.sell.po.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public class IOrderMasterDaoTest extends SellApplicationTests {

    @Autowired
    private IOrderMasterDao masterDao;

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("87654321");
        orderMaster.setBuyerName("小妖");
        orderMaster.setBuyerPhone("12345678912");
        orderMaster.setBuyerAddress("内蒙古草原");
        orderMaster.setBuyerOpenid("123123");
        orderMaster.setOrderAmount(new BigDecimal("200"));
        OrderMaster result = masterDao.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        Pageable request = PageRequest.of(0,3);
        Page<OrderMaster> result = masterDao.findByBuyerOpenid("123123", request);
        Assert.assertNotEquals(0,result.getTotalElements());
    }
}