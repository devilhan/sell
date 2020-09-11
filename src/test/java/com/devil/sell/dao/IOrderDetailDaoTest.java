package com.devil.sell.dao;

import com.devil.sell.SellApplicationTests;
import com.devil.sell.po.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class IOrderDetailDaoTest extends SellApplicationTests {

    @Autowired
    private IOrderDetailDao detailDao;

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234568");
        orderDetail.setOrderId("12345678");
        orderDetail.setProductId("12341244");
        orderDetail.setProductName("牛肉石锅");
        orderDetail.setProductPrice(new BigDecimal(35.9));
        orderDetail.setProductIcon("http://www.dajgkdls");
        orderDetail.setProductQuantity(2);

        OrderDetail result = detailDao.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> result = detailDao.findByOrderId("12345678");
        Assert.assertNotEquals(0,result.size());
    }
}
