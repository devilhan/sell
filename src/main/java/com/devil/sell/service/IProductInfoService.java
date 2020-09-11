package com.devil.sell.service;

import com.devil.sell.dto.CartDTO;
import com.devil.sell.po.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/6/10
 */
public interface IProductInfoService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findClientAll();

    Page<ProductInfo> findServerAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOS);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOS);

    //商品上架
    ProductInfo onSale(String productId);

    //商品下架
    ProductInfo offSale(String productId);

}
