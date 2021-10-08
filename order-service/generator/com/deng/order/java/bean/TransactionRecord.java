package com.deng.order.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;
import java.util.Date;


@Data
public class TransactionRecord extends IDEntity {

    private Integer id;
	
	private String orderSn;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	/**
	 * 事件详情
	*/
	private String events;
	
	/**
	 * 结果详情
	*/
	private String result;
	
	
}