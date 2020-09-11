package com.devil.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类目及商品
 * @author Hanyanjiao
 * @date 2020/6/10
 */

@Data
public class ProductVo implements Serializable {

    private static final long serialVersionUID = 545271996040238321L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVos;

}
