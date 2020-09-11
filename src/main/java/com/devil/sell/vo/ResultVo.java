package com.devil.sell.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回前端对象
 * @author Hanyanjiao
 * @date 2020/6/10
 */

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -5126464486442038187L;

    //返回码
    private Integer code;

    //返回信息
    private String msg;

    //数据内容
    private T data;

}
