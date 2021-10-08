package com.dora.commonservice.entity;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;

/**
 * 功能操作资源表
 * @Author: dengrisheng
 * @Date: 2021-09-29 23:32:20
 * @Version: v1.0
 */
@Data
public class Operation extends IDEntity {

    private Long id;
	
	/**
	 * 功能名称
	*/
	private String operationName;
	
	/**
	 * 功能编码
	*/
	private String operationNo;
	
	/**
	 * 功能链接URL
	*/
	private String interceptUrl;
	
	/**
	 * 功能父id
	*/
	private Long parentId;
	
	private Date createTime;
	
	
}