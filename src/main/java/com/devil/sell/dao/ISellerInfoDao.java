package com.devil.sell.dao;

import com.devil.sell.po.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Hanyanjiao
 * @date 2020/6/29
 */
public interface ISellerInfoDao extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);

}
