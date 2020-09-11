package com.devil.sell.dao;

import com.devil.sell.po.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/6/10
 */
public interface IProductInfoDao extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
