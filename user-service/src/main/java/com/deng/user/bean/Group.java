package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;


@Data
public class Group extends IDEntity {

    /**
	 * 用户组名称
	*/
	private String groupName;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	
}