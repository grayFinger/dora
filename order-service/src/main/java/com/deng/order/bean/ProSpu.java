package com.deng.order.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;
import java.util.Date;


@Data
public class ProSpu extends IDEntity {

    private Long id;
	
	/**
	 * 商品名称
	*/
	private String spuName;
	
	/**
	 * 商品子标题
	*/
	private String subTitle;
	
	/**
	 * 1级类目
	*/
	private Long cid1;
	
	/**
	 * 2级类目
	*/
	private Long cid2;
	
	/**
	 * 3级类目
	*/
	private Long cid3;
	
	/**
	 * 是否上架 0：下架，1：上架目
	*/
	private Byte saleable;
	
	/**
	 * 是否删除 0：已删除，1：有效
	*/
	private Byte isTrue;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	/**
	 * 创建人id
	*/
	private Long userId;
	
	/**
	 * 创建人名称
	*/
	private String userName;
	
	/**
	 * 最后更新时间
	*/
	private Date lastUpdateTime;
	
	
}