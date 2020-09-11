package com.devil.sell.service.impl;

import com.devil.sell.dao.IProductCategoryDao;
import com.devil.sell.po.ProductCategory;
import com.devil.sell.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/6/10
 */

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private IProductCategoryDao categoryDao;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return categoryDao.findById(categoryId).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return categoryDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryDao.save(productCategory);
    }
}
