package com.devil.sell.service.impl;

import com.devil.sell.dao.ISellerInfoDao;
import com.devil.sell.po.SellerInfo;
import com.devil.sell.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Hanyanjiao
 * @date 2020/6/29
 */

@Service
public class SellerServiceImpl implements ISellerService {

    @Autowired
    private ISellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoDao.findByOpenid(openid);
    }
}
