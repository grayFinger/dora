package com.deng.user.bean;

import com.dora.common.db.bean.IDEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class UserInfo extends IDEntity {

    /**
	 * 用户状态(0:正常，1:注销)
	*/
	private Integer status;
	
	/**
	 * 用户名称
	*/
	private String username;
	
	/**
	 * 用户昵称
	*/
	private String nickname;
	
	/**
	 * 登录账号
	*/
	private String account;
	
	/**
	 * 登录密码
	*/
	private String passwd;
	
	/**
	 * 手机号
	*/
	private String phone;
	
	/**
	 * 身份证号
	*/
	private String idCard;
	
	/**
	 * 性别(0:男，1:女)
	*/
	private Integer gender;
	
	/**
	 * 出生日期
	*/
	private Date birthday;
	
	/**
	 * 邮箱
	*/
	private String mail;
	
	/**
	 * 头像地址
	*/
	private String avatar;
	
	/**
	 * 地址
	*/
	private String address;
	
	/**
	 * 创建时间
	*/
	private Date createTime;
	
	/**
	 * 更新时间
	*/
	private Date updateTime;
	
	/**
	 * 用户类型(0:微信用户,1:pc用户,2:支付宝用户)
	*/
	private Integer userType;
	
	/**
	 * 引荐人编号
	*/
	private String referrerNo;
	
	/**
	 * 推荐编号
	*/
	private String promoteNo;

	private List<Menu> menus;

	private List<Operation> operations;

	private List<PageElement> pageElements;

}