package com.devil.sell.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Hanyanjiao
 * @date 2020/6/29
 */

@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;

}
