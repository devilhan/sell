package com.devil.sell.service.impl;

import com.devil.sell.converter.OrderMaster2OrderDTOConverter;
import com.devil.sell.dao.IOrderDetailDao;
import com.devil.sell.dao.IOrderMasterDao;
import com.devil.sell.dto.CartDTO;
import com.devil.sell.dto.OrderDTO;
import com.devil.sell.enums.OrderStatusEnum;
import com.devil.sell.enums.PayStatusEnum;
import com.devil.sell.enums.ResultEnum;
import com.devil.sell.exception.SellException;
import com.devil.sell.po.OrderDetail;
import com.devil.sell.po.OrderMaster;
import com.devil.sell.po.ProductInfo;
import com.devil.sell.service.IOrderService;
import com.devil.sell.service.IPayService;
import com.devil.sell.service.IProductInfoService;
import com.devil.sell.service.IPushMessageService;
import com.devil.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hanyanjiao
 * @date 2020/6/11
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private IPayService payService;

    @Autowired
    private IOrderMasterDao masterDao;

    @Autowired
    private IOrderDetailDao detailDao;

    @Autowired
    private IPushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();

        BigDecimal orderAmount = new BigDecimal(0);

        //查询商品数量、价格
        for (OrderDetail orderDetail : orderDTO.getOrderDetails()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //计算总价格
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);

            detailDao.save(orderDetail);

        }

        //写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);

        masterDao.save(orderMaster);


        //TODO 锁机制来控制多线程访问
        //扣库存
        List<CartDTO> cartDTOS = orderDTO.getOrderDetails().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());

        productInfoService.decreaseStock(cartDTOS);

        //发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster master = masterDao.findById(orderId).orElse(null);
        if(master == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> details = detailDao.findByOrderId(orderId);

        if(CollectionUtils.isEmpty(details)){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO dto = new OrderDTO();
        dto.setOrderDetails(details);
        BeanUtils.copyProperties(master,dto);


        return dto;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> masters = masterDao.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOS = OrderMaster2OrderDTOConverter.convert(masters.getContent());

        return new PageImpl<>(orderDTOS, pageable, masters.getTotalElements());
    }

    @Transactional
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster master = new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,master);
        OrderMaster updateResult = masterDao.save(master);
        if(updateResult == null ){
            log.error("【取消订单】 更新失败，orderMaster={}",master);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返还库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetails())){
            log.error("【取消订单】 订单中无订单详情，orderId={}",orderDTO.getOrderId());
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOS = orderDTO.getOrderDetails().stream().map( e->
                new CartDTO(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOS);

        //已支付订单进行退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Transactional
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster master = new OrderMaster();
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】 订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,master);
        OrderMaster updateResult = masterDao.save(master);
        if(updateResult == null ){
            log.error("【完结订单】 更新失败，orderMaster={}",master);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模板消息
        pushMessageService.orderStatus(orderDTO);
        return orderDTO;
    }

    @Transactional
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster master = new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】 订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            log.error("【支付订单】 订单支付状态不正确,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,master);
        OrderMaster updateResult = masterDao.save(master);
        if(updateResult == null ){
            log.error("【支付订单】 更新失败，orderMaster={}",master);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<OrderMaster> masters = masterDao.findAll(pageable);
        List<OrderDTO> orderDTOS = OrderMaster2OrderDTOConverter.convert(masters.getContent());

        return new PageImpl<>(orderDTOS, pageable, masters.getTotalElements());
    }
}
