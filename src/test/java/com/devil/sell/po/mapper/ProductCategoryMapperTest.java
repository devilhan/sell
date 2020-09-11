package com.devil.sell.po.mapper;

import com.devil.sell.po.ProductCategory;
import com.devil.sell.service.impl.SellerServiceImplTest;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProductCategoryMapperTest extends SellerServiceImplTest {

    @Autowired
    private ProductCategoryMapper categoryMapper;

    @Test
    public void insertByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("category_name","饮料甜点");
        map.put("category_type",7);
        int result = categoryMapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }

    @Test
    public void insertByObject(){
        ProductCategory category = new ProductCategory();
        category.setCategoryName("特价优惠");
        category.setCategoryType(8);
        int result = categoryMapper.insertByObject(category);
        Assert.assertEquals(1,result);
    }

    @Test
    public void findByCategoryType(){
        ProductCategory category = categoryMapper.findByCategoryType(8);
        Assert.assertNotNull(category);
    }

    @Test
    public void findByCategoryName(){
        List<ProductCategory> categories = categoryMapper.findByCategoryName("优惠套餐");
        Assert.assertNotEquals(0,categories.size());
    }

    @Test
    public void updateByCategoryType(){
        int result = categoryMapper.updateByCategoryType("黑暗料理", 8);
        Assert.assertEquals(1,result);
    }

    @Test
    public void updateByObject(){
        ProductCategory category = new ProductCategory();
        category.setCategoryType(7);
        category.setCategoryName("诱惑卤味");
        int result = categoryMapper.updateByObject(category);
        Assert.assertEquals(1,result);
    }

    @Test
    public void deleteByCategoryType(){
        int result = categoryMapper.deleteByCategoryType(7);
        Assert.assertEquals(1,result);
    }

    @Test
    public void selectByCategoryType(){
        ProductCategory category = categoryMapper.selectByCategoryType(2);
        Assert.assertNotNull(category);
    }
}