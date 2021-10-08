package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class PowerFile extends IDEntity {

    /**
	 * 权限id
	*/
	private Long powerId;
	
	/**
	 * 文件id
	*/
	private Long fileId;
	
	
}