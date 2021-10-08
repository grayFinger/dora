package com.deng.order.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class SkuAttributeValue extends IDEntity {

    private Long id;
	
	/**
	 * 扩展属性值
	*/
	private String skuValue;
	
	/**
	 * 关联的扩展属性id
	*/
	private Long skuKeyId;
	
	
}