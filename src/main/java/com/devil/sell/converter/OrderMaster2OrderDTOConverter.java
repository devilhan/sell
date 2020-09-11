package com.devil.sell.converter;

import com.devil.sell.dto.OrderDTO;
import com.devil.sell.po.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderMaster 转为 OrderDTO
 * @author Hanyanjiao
 * @date 2020/6/12
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster master){
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(master,dto);
        return dto;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e ->
            convert(e)
        ).collect(Collectors.toList());
    }
}
