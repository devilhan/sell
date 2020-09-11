package com.devil.sell.service.impl;

import com.devil.sell.SellApplicationTests;
import com.devil.sell.po.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;


@Slf4j
public class CategoryServiceImplTest extends SellApplicationTests {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory category = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),category.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> categories = categoryService.findAll();
        Assert.assertNotEquals(0,categories.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3));
        Assert.assertNotEquals(0,categories.size());
    }

    @Test
    public void save() {
        ProductCategory category = new ProductCategory("男神套餐",4);
        ProductCategory result = categoryService.save(category);
        Assert.assertNotNull(result);
    }
}
