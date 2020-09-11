package com.devil.sell.po.mapper;

import com.devil.sell.po.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author Hanyanjiao
 * @date 2020/7/1
 */
public interface ProductCategoryMapper {

    /**
     * 注解方式
     * @param map
     * @return
     */
    @Insert("insert into product_category(category_name,category_type) values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);

    /**
     * 对象方式
     * @param category
     * @return
     */
    @Insert("insert into product_category(category_name,category_type) values (#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory category);

    /**
     * 通过类目类型查询
     * @param categoryType
     * @return
     */
    @Select("select * from product_category where category_type=#{categoryType}")
    @Results({
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName")
    })
    ProductCategory findByCategoryType(Integer categoryType);

    /**
     * 通过类目名称查询
     * @param categoryName
     * @return
     */
    @Select("select * from product_category where category_name=#{categoryName}")
    @Results({
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName")
    })
    List<ProductCategory> findByCategoryName(String categoryName);


    /**
     * 通过类目类型更新
     * @param categoryName
     * @param categoryType
     * @return
     */
    @Update("update product_category set category_name = #{categoryName} where category_type= #{categoryType}")
    int updateByCategoryType(@Param("categoryName") String categoryName,
                             @Param("categoryType") Integer categoryType);


    /**
     * 通过对象更新
     * @param category
     * @return
     */
    @Update("update product_category set category_name = #{categoryName} where category_type= #{categoryType}")
    int updateByObject(ProductCategory category);

    @Delete("delete from product_category where category_type=#{categoryType}")
    int deleteByCategoryType(Integer categoryType);

    /**
     * xml文件通过类型查询
     * @param categoryType
     * @return
     */
    ProductCategory selectByCategoryType(Integer categoryType);
}
