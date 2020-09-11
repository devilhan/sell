package com.devil.sell.form;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Hanyanjiao
 * @date 2020/6/29
 */

@Data
public class ProductForm {

    private String productId;

    //商品名称
    private String productName;

    //商品价格
    private BigDecimal productPrice;

    //商品库存
    private Integer productStock;

    //商品描述
    private String productDescription;

    //商品图片
    private String productIcon;

    //所属类目
    private Integer categoryType;
}
