package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;


@Data
public class PageElement extends IDEntity {

    /**
	 * 页面元素名称
	*/
	private String elementName;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	
}