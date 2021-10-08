package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;


@Data
public class Role extends IDEntity {

    /**
	 * 角色名称
	*/
	private String roleName;
	
	/**
	 * 状态(0:正常，1:删除)
	*/
	private Byte status;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	
}