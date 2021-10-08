package com.deng.user.controllers;

import com.deng.user.bean.UserInfo;
import com.deng.user.service.UserInfoService;
import com.deng.user.vo.UserInfoVO;
import com.dora.common.annotation.web.Post;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/register")
    public void saveUser(@RequestBody UserInfo userInfo){

        userInfo.setStatus(0);
        userInfo.setUserType(0);
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        userInfoService.insert(userInfo);
    }

    /**
     * 根据账号查询用户信息
     * @param account
     * @return
     */
    @GetMapping("/getUserByAccount")
    public UserInfo getUserByAccount(String account){
        UserInfo user = new UserInfo();
        user.setAccount(account);
       return userInfoService.selectOne(user);
    }

    /**
     * 根据账号查询用户权限
     *
     */
    @GetMapping("/getUserAuthority")
    public UserInfo getUserAuthority(String account){
        return userInfoService.getUserAuthorityVO(account);
    }
}
