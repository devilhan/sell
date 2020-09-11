package com.devil.sell.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 *
 *  商品类目信息
 * @author Hanyanjiao
 * @date 2020/6/10
 */


@Data
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    //类目Id
    @Id
    @GeneratedValue
    private Integer categoryId;

    //类目名称
    private String  categoryName;

    //类目类型
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
