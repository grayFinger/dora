package com.dora.commonservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserAuthorityVO implements Serializable {
    private Long id;

    /**
     * 用户状态(0:正常，1:注销)
     */
    private int status;

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
    private int gender;

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
    private int userType;

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

    public UserAuthorityVO(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<PageElement> getPageElements() {
        return pageElements;
    }

    public void setPageElements(List<PageElement> pageElements) {
        this.pageElements = pageElements;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getReferrerNo() {
        return referrerNo;
    }

    public void setReferrerNo(String referrerNo) {
        this.referrerNo = referrerNo;
    }

    public String getPromoteNo() {
        return promoteNo;
    }

    public void setPromoteNo(String promoteNo) {
        this.promoteNo = promoteNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
