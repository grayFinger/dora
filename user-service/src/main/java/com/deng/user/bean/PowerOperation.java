package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class PowerOperation extends IDEntity {

    /**
	 * 权限id
	*/
	private Long powerId;
	
	/**
	 * 功能操作id
	*/
	private Long operationId;
	
	
}