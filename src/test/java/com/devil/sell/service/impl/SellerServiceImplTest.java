package com.devil.sell.service.impl;

import com.devil.sell.SellApplicationTests;
import com.devil.sell.po.SellerInfo;
import com.devil.sell.service.ISellerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class SellerServiceImplTest extends SellApplicationTests {

    @Autowired
    private ISellerService sellerService;

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid("abc");
        log.info("sellerInfo ={}",sellerInfo);
        Assert.assertNotNull(sellerInfo);
    }
}