package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;


@Data
public class UserFile extends IDEntity {

    /**
	 * 状态(0:正常，1:删除)
	*/
	private Byte status;
	
	/**
	 * 文件名称
	*/
	private String fileName;
	
	/**
	 * 文件路径
	*/
	private String fileUrl;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	
}