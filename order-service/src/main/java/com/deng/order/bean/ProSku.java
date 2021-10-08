package com.deng.order.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;
import java.util.Date;


@Data
public class ProSku extends IDEntity {

    private Long id;
	
	/**
	 * 商品规格名称
	*/
	private String skuName;
	
	/**
	 * 规格所属商品id
	*/
	private Long spuId;
	
	/**
	 * 库存
	*/
	private Integer spuStock;
	
	/**
	 * 售价
	*/
	private Integer spuPrice;
	
	/**
	 * 商品来源
	*/
	private String spuSource;
	
	/**
	 * 销量
	*/
	private Integer spuSales;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	/**
	 * 创建用户id
	*/
	private Long userId;
	
	/**
	 * 创建用户名称
	*/
	private String userName;
	
	/**
	 * 最后一次更新时间
	*/
	private Date lateUpdateTime;
	
	
}