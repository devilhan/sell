package com.devil.sell.service.impl;

import com.devil.sell.config.UpYunConfig;
import com.devil.sell.dao.IProductInfoDao;
import com.devil.sell.dto.CartDTO;
import com.devil.sell.enums.ResultEnum;
import com.devil.sell.exception.SellException;
import com.devil.sell.po.ProductInfo;
import com.devil.sell.enums.ProductStatusEnum;
import com.devil.sell.service.IProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Hanyanjiao
 * @date 2020/6/10
 */
@Service
//@CacheConfig(cacheNames = "product")
public class ProductInfoServiceImpl implements IProductInfoService {

    @Autowired
    private IProductInfoDao productInfoDao;

    @Autowired
    private UpYunConfig upYunConfig;

    @Override
//    @Cacheable(cacheNames = "product",key = "123",)
    //key的默认值是参数
//    @Cacheable(key = "123")
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> productInfo = productInfoDao.findById(productId);
       /* if(productInfo.isPresent()){
            return productInfo.get().addImageHost(upYunConfig.getImageHost());
        }
        return null;*/
       productInfo.ifPresent(e -> e.addImageHost(upYunConfig.getImageHost()));
       return productInfo.orElse(null);
    }

    @Override
    public List<ProductInfo> findClientAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findServerAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
//    @CachePut(cacheNames = "product",key = "123")
//    @CachePut(key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO cartDTO : cartDTOS) {
            ProductInfo productInfo = productInfoDao.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO cartDTO : cartDTOS) {
            ProductInfo productInfo = productInfoDao.findById(cartDTO.getProductId()).orElse(null);
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();

            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            productInfoDao.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoDao.findById(productId).orElse(null);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if(productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoDao.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoDao.findById(productId).orElse(null);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if(productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoDao.save(productInfo);
    }
}
