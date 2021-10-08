package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class PowerPageElement extends IDEntity {

    /**
	 * 权限id
	*/
	private Long powerId;
	
	/**
	 * 页面元素id
	*/
	private Long pageElementId;
	
	
}