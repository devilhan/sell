package com.devil.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hanyanjiao
 * @date 2020/6/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    //商品Id
    private String productId;

    //商品数量
    private Integer productQuantity;
}
