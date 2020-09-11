package com.devil.sell.dao;

import com.devil.sell.SellApplicationTests;
import com.devil.sell.po.SellerInfo;
import com.devil.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class ISellerInfoDaoTest extends SellApplicationTests {

    @Autowired
    private ISellerInfoDao sellerInfoDao;

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo info = sellerInfoDao.save(sellerInfo);
        Assert.assertNotNull(info);
    }


    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = sellerInfoDao.findByOpenid("abc");
        log.info("sellerInfo is {}",sellerInfo);
        Assert.assertNotNull(sellerInfo);
    }
}