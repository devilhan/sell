package com.devil.sell.service;

import com.devil.sell.po.SellerInfo;

/**
 * @author Hanyanjiao
 * @date 2020/6/29
 */
public interface ISellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
