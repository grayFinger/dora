package com.dora.commonservice.entity;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

/**
 * 页面元素表
 * @Author: dengrisheng
 * @Date: 2021-09-29 23:32:20
 * @Version: v1.0
 */

@Data
public class PageElement extends IDEntity {

    private Long id;
	
	/**
	 * 页面元素名称
	*/
	private String elementName;
	
	/**
	 * 创建时间
	*/
	private Integer createTime;
	
	
}