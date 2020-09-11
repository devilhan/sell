package com.devil.sell.form;

import lombok.Data;

/**
 * @author Hanyanjiao
 * @date 2020/6/29
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    //类目名称
    private String  categoryName;

    //类目类型
    private Integer categoryType;
}
