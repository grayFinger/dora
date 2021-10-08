package com.deng.order.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;


@Data
public class ProCategory extends IDEntity {

    private Long id;
	
	/**
	 * 父级id
	*/
	private Long parentId;
	
	/**
	 * 分类名称
	*/
	private String cName;
	
	/**
	 * 分类级别0，1，2
	*/
	private Boolean cLevel;
	
	/**
	 * 分类图标路径
	*/
	private String cIcon;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	/**
	 * 评论内容
	*/
	private Long userId;
	
	/**
	 * 用户名称
	*/
	private String userName;
	
	/**
	 * 是否删除0：，已删除，1：正常
	*/
	private Boolean isTrue;
	
	/**
	 * 分类详情
	*/
	private String description;
	
	
}