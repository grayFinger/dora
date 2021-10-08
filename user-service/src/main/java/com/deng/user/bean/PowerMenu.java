package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class PowerMenu extends IDEntity {

    /**
	 * 权限id
	*/
	private Long powerId;
	
	/**
	 * 菜单id
	*/
	private Long menuId;
	
	
}