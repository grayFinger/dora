package com.deng.order.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;
import java.util.Date;


@Data
public class OrderReturnsApply extends IDEntity {

    private Long id;
	
	/**
	 * 订单单号
	*/
	private String orderNo;
	
	/**
	 * 子订单编码
	*/
	private String orderDetailId;
	
	/**
	 * 售后单号
	*/
	private String returnNo;
	
	/**
	 * 用户id
	*/
	private Long userId;
	
	/**
	 * 类型 0 仅退款 1退货退款
	*/
	private Byte state;
	
	/**
	 * 货物状态 0:已收到货 1:未收到货
	*/
	private Byte productStatus;
	
	/**
	 * 退换货原因
	*/
	private String why;
	
	/**
	 * 审核状态 -1 拒绝 0 未审核 1审核通过
	*/
	private Byte status;
	
	/**
	 * 审核时间
	*/
	private Integer auditTime;
	
	/**
	 * 审核原因
	*/
	private String auditWhy;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	/**
	 * 备注
	*/
	private String note;
	
	
}