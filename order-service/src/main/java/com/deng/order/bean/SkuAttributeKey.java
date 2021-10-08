package com.deng.order.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;
import java.util.Date;


@Data
public class SkuAttributeKey extends IDEntity {

    private Long id;
	
	/**
	 * 关联的规格id
	*/
	private Long skuId;
	
	/**
	 * 扩展属性名称
	*/
	private String keyName;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	/**
	 * 最后更新时间
	*/
	private Date lateUpdateTime;
	
	/**
	 * 创建用户id
	*/
	private Long userId;
	
	/**
	 * 创建用户名称
	*/
	private String userName;
	
	/**
	 * 是否删除 0：已删除，1：有效
	*/
	private Byte isTrue;
	
	
}