package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;


@Data
public class Operation extends IDEntity {

    /**
	 * 功能名称
	*/
	private String operationName;
	
	/**
	 * 功能编码
	*/
	private String operationNo;
	
	/**
	 * 接口URL
	*/
	private String interceptUrl;
	
	/**
	 * 功能父id
	*/
	private Long parentId;
	
	private Date createTime;
	
	
}