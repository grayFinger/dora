package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;


@Data
public class OperationLog extends IDEntity {

    /**
	 * 功能id
	*/
	private Long operationId;
	
	/**
	 * 操作内容
	*/
	private String operationContent;
	
	/**
	 * 操作用户id
	*/
	private Long userId;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	
}