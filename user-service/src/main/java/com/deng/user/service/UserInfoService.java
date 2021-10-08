package com.deng.user.service;

import com.deng.user.vo.UserInfoVO;
import com.dora.common.db.service.BaseService;
import com.deng.user.bean.UserInfo;
import com.deng.user.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 16:56:01
 */

@Service
public class UserInfoService extends BaseService<UserInfo> {

    @Autowired
    private UserInfoDao userInfoDao;


    public UserInfo getUserAuthorityVO(String account){
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(account);
        List<UserInfo> user = userInfoDao.findByExample(userInfo);
        UserInfo result = userInfoDao.selectUserPowers(userInfo);
        return result;
    }
}
