package com.fanye.sell.dto;

import com.fanye.sell.dataobject.OrderDetail;
import com.fanye.sell.enums.OrderStatusEnum;
import com.fanye.sell.enums.PayStatusEnum;
import com.fanye.sell.utils.EnumUtil;
import com.fanye.sell.utils.Serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /**
     * 订单id
     **/
    private String orderId;

    /**
     * 买家名字
     **/
    private String buyerName;

    /**
     * 买家手机号
     **/
    private String buyerPhone;

    /**
     * 买家地址
     **/
    private String buyerAddress;

    /**
     * 买家微信openid
     **/
    private String buyerOpenid;

    /**
     * 订单总金额
     **/
    private BigDecimal orderAmount;

    /**
     * 订单状态，默认为0 新下单
     **/
    private Integer orderStatus;

    /**
     * 支付状态，默认为0 为支付
     **/
    private Integer payStatus;

    /**
     * 创建时间
     **/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间
     **/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
