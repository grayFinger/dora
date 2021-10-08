package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class GroupRole extends IDEntity {

    /**
	 * 角色id
	*/
	private Long roleId;
	
	/**
	 * 用户组id
	*/
	private Long groupId;
	
	
}