package com.deng.user.dao;


import com.dora.common.db.dao.BaseDao;
import com.deng.user.bean.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库操作
 * @author conta
 * @create 2021-09-28 16:56:01
 */
public interface UserInfoDao extends BaseDao<UserInfo> {

     UserInfo selectUserPowers(@Param("userInfo") UserInfo userInfo);
}