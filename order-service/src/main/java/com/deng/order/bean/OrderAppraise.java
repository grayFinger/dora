package com.deng.order.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;
import java.util.Date;


@Data
public class OrderAppraise extends IDEntity {

    private Long id;
	
	/**
	 * 订单编码
	*/
	private Long orderId;
	
	/**
	 * 级别 -1差评 0中评 1好评
	*/
	private String level;
	
	/**
	 * 描述相符 1-5
	*/
	private Byte descStar;
	
	/**
	 * 物流服务 1-5
	*/
	private Byte logisticsStar;
	
	/**
	 * 服务态度 1-5
	*/
	private Byte attitudeStar;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	/**
	 * 评论内容
	*/
	private String info;
	
	
}