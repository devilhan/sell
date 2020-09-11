package com.devil.sell.dao;

import com.devil.sell.SellApplicationTests;
import com.devil.sell.po.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;


@Slf4j
public class IProductInfoDaoTest extends SellApplicationTests {

    @Autowired
    private IProductInfoDao productInfoDao;

    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("12345678");
        productInfo.setProductName("石锅拌饭");
        productInfo.setProductPrice(new BigDecimal(26.5));
        productInfo.setProductStock(5);
        productInfo.setProductDescription("精品牛肉+各种小菜");
        productInfo.setProductIcon("");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(1);
        ProductInfo result = productInfoDao.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfos = productInfoDao.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfos.size());
    }
}
