package com.dora.commonservice.entity;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

/**
 * 菜单表
 * @Author: dengrisheng
 * @Date: 2021-09-29 23:32:20
 * @Version: v1.0
 */

@Data
public class Menu extends IDEntity {

    private Long id;
	
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
	private Integer createTime;
	
	/**
	 * 创建时间
	*/
	private Integer updateTime;
	
	
}