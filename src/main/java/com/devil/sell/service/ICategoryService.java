package com.devil.sell.service;

import com.devil.sell.po.ProductCategory;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/6/10
 */
public interface ICategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
