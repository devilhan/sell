package com.devil.sell.dao;


import com.devil.sell.SellApplicationTests;
import com.devil.sell.po.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Transactional
public class IProductCategoryTest extends SellApplicationTests {

    @Autowired
    private IProductCategoryDao categoryDao;

    @Test
    public void findOne(){
        ProductCategory category = categoryDao.findById(1).orElse(null);
        log.info("category is {}",category.toString());
    }

    @Test
    public void save(){
        ProductCategory category = new ProductCategory("女神套餐",3);
        ProductCategory result = categoryDao.saveAndFlush(category);
        log.info("category is {}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void update(){
        ProductCategory category = categoryDao.findById(2).orElse(null);
        category.setCategoryName("优惠套餐");
        category.setCategoryType(1);
        log.info("category is {}",categoryDao.save(category));
    }

    @Test
    public void findByCategoryType(){
        List<Integer> list = Arrays.asList(1,2,3);

        List<ProductCategory> categories = categoryDao.findByCategoryTypeIn(list);
        Assert.assertNotEquals(1,categories.size());
    }
}