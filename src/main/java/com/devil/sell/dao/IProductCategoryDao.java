package com.devil.sell.dao;

import com.devil.sell.po.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/6/10
 */
public interface IProductCategoryDao extends JpaRepository<ProductCategory,Integer> {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTyleList);
}
