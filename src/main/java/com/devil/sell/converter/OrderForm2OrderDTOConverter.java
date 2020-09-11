package com.devil.sell.converter;

import com.devil.sell.dto.OrderDTO;
import com.devil.sell.enums.ResultEnum;
import com.devil.sell.exception.SellException;
import com.devil.sell.form.OrderForm;
import com.devil.sell.po.OrderDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/6/12
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm form){
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(form.getName());
        orderDTO.setBuyerPhone(form.getPhone());
        orderDTO.setBuyerOpenid(form.getOpenid());
        orderDTO.setBuyerAddress(form.getAddress());
        List<OrderDetail> details;
        try {
            details = gson.fromJson(form.getItems()
                    ,new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【对象转换】 错误，String={}",form.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetails(details);

        return orderDTO;
    }
}
