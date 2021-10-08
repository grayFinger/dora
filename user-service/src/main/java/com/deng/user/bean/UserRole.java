package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class UserRole extends IDEntity {

    /**
	 * 用户id
	*/
	private Long userId;
	
	/**
	 * 角色id
	*/
	private Long roleId;
	
	
}