package com.devil.sell.service.impl;

import com.devil.sell.config.WxAccountConfig;
import com.devil.sell.dto.OrderDTO;
import com.devil.sell.service.IPushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Hanyanjiao
 * @date 2020/6/30
 */

@Slf4j
@Service
public class PushMessageServiceImpl implements IPushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO dto) {
        WxMpTemplateMessage message = new WxMpTemplateMessage();
        message.setToUser(dto.getBuyerOpenid());
        message.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","亲，记得收货"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2","123456789"),
                new WxMpTemplateData("keyword3",dto.getOrderId()),
                new WxMpTemplateData("keyword4",dto.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("keyword5","￥"+dto.getOrderAmount()),
                new WxMpTemplateData("remark","祝您用餐愉快，欢迎下次光临!")
        );
        message.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(message);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】 发送失败，{}",e);
        }
    }
}
