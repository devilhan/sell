package com.devil.sell.dao;

import com.devil.sell.po.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/6/11
 */
public interface IOrderDetailDao extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String orderId);
}
