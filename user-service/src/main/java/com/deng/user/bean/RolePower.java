package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class RolePower extends IDEntity {

    /**
	 * 权限id
	*/
	private Long roleId;
	
	/**
	 * 功能操作id
	*/
	private Long powerId;
	
	
}