package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class GroupUser extends IDEntity {

    /**
	 * 用户id
	*/
	private Long userId;
	
	/**
	 * 用户组id
	*/
	private Long groupId;
	
	
}