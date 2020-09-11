package com.devil.sell.controller;

import com.devil.sell.po.ProductCategory;
import com.devil.sell.po.ProductInfo;
import com.devil.sell.service.ICategoryService;
import com.devil.sell.service.IProductInfoService;
import com.devil.sell.utils.ResultVoUtil;
import com.devil.sell.vo.ProductInfoVo;
import com.devil.sell.vo.ProductVo;
import com.devil.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品接口
 * @author Hanyanjiao
 * @date 2020/6/10
 */
@RestController
@RequestMapping("/buyer/product")
@CacheConfig(cacheNames = "productList")
public class BuyerProductController {

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private ICategoryService categoryService;

    //商品列表
    @GetMapping("/list")
    @Cacheable(key = "123",unless = "#result.getCode() == 0")
    public ResultVo list(){
        //查询所有上架商品
        List<ProductInfo> productInfos = productInfoService.findClientAll();

        //查询类目（一次性查询）
        List<Integer> categoryTypeList = productInfos.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVo> productVos = new ArrayList<>();
        //数据拼装
        for (ProductCategory category : categories) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryType(category.getCategoryType());
            productVo.setCategoryName(category.getCategoryName());

            List<ProductInfoVo> productInfoVos = new ArrayList<>();
            for (ProductInfo productInfo : productInfos) {
                if(productInfo.getCategoryType().equals(category.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVos.add(productInfoVo);
                }
            }
            productVo.setProductInfoVos(productInfoVos);
            productVos.add(productVo);
        }

        return ResultVoUtil.success(productVos);
    }


}
