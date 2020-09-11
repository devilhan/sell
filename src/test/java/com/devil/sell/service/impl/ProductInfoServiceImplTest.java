package com.devil.sell.service.impl;


import com.devil.sell.SellApplicationTests;
import com.devil.sell.po.ProductInfo;
import com.devil.sell.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class ProductInfoServiceImplTest extends SellApplicationTests {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("12345678");
        Assert.assertEquals("12345678",productInfo.getProductId());
    }

    @Test
    public void findClientAll() {
        List<ProductInfo> productInfos = productInfoService.findClientAll();
        Assert.assertNotEquals(0,productInfos.size());
    }

    @Test
    public void findServerAll() {
        Pageable request = PageRequest.of(0,2);
        Page<ProductInfo> productInfos = productInfoService.findServerAll(request);
//        log.info("page total elements' count is {}",productInfos.getTotalElements());
        Assert.assertNotEquals(0,productInfos.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("87654321");
        productInfo.setProductName("水果寿司");
        productInfo.setProductPrice(new BigDecimal(34.9));
        productInfo.setProductStock(10);
        productInfo.setProductDescription("新鲜水果+鹿茸汤");
        productInfo.setProductIcon("");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(1);
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSale(){
        ProductInfo productInfo = productInfoService.onSale("87654321");
        Assert.assertEquals(ProductStatusEnum.UP,productInfo.getProductStatusEnum());
    }

    @Test
    public void offSale(){
        ProductInfo productInfo = productInfoService.offSale("87654321");
        Assert.assertEquals(ProductStatusEnum.DOWN,productInfo.getProductStatusEnum());
    }
}
