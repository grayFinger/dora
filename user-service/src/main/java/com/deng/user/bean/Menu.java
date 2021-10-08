package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;
import java.util.Date;


@Data
public class Menu extends IDEntity {

    /**
	 * 菜单父id
	*/
	private Long parentId;
	
	/**
	 * 菜单名称
	*/
	private String menuName;
	
	/**
	 * 菜单路径
	*/
	private String menuUrl;
	
	/**
	 * 菜单图标
	*/
	private String menuIcon;
	
	/**
	 * 状态(0:正常，1:删除)
	*/
	private Byte status;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	/**
	 * 创建时间
	*/
	private Date updateTime;
	
	
}