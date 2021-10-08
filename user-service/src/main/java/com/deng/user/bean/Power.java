package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;



@Data
public class Power extends IDEntity {

    /**
	 * 权限类型
	*/
	private String powerType;
	
	
}