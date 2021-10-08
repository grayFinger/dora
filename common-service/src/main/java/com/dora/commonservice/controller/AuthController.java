package com.dora.commonservice.controller;

import com.dora.common.exception.BusinessException;
import com.dora.common.utils.MD5;
import com.dora.commonservice.constants.ResponseStatus;
import com.dora.commonservice.entity.TokenModel;
import com.dora.commonservice.entity.UserAuthorityVO;
import com.dora.commonservice.entity.UserInfoModel;

import com.dora.commonservice.service.RedisService;
import com.dora.commonservice.service.userService.UserService;
import com.dora.commonservice.utils.JwtTokenUtil;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: AuthController
 * @Author: zhaolianqi
 * @Date: 2021/8/23 11:41
 * @Version: v1.0
 */
@RefreshScope
@RestController
@RequestMapping("/api/common/userAuth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    /**
     * 登录
     * @param
     * @return
     */
    @PostMapping("/signin")
    public TokenModel authenticateUser(@RequestBody UserInfoModel login) {
        if (StringUtils.isEmpty(login.getAccount())){
           throw new BusinessException(ResponseStatus.USER_ACCOUNT_CANT_BE_EMPTY.getCode()+"",
                   ResponseStatus.USER_ACCOUNT_CANT_BE_EMPTY.getMsg());
        }
        if (StringUtils.isEmpty(login.getPasswd())){
            throw new BusinessException("1004","密码不能为空");
        }
        UserAuthorityVO user = userService.getUserAuthority(login.getAccount());
        if (user == null){
            throw new BusinessException(ResponseStatus.USER_ACCOUNT_NOT_EXIST.getCode()+"",
                    ResponseStatus.USER_ACCOUNT_NOT_EXIST.getMsg());
        }
        if (!user.getPasswd().equals(MD5.MD5Encode(login.getPasswd()))){
            throw new BusinessException(ResponseStatus.USER_ACCOUNT_OR_PWD_WRONG.getCode()+"",
                    ResponseStatus.USER_ACCOUNT_OR_PWD_WRONG.getMsg());
        }

        TokenModel refreshToken = new TokenModel();
        refreshToken.setToken(JwtTokenUtil.generateToken(user,"access_token"));
        refreshToken.setRefreshToken(JwtTokenUtil.generateToken(user,"refresh_token"));
        refreshToken.setUser(user);
        int seconds = 3600*24*7;
        redisService.putMapCache("user-auth-"+user.getId(),user.getId().toString(),refreshToken,seconds);
        return refreshToken;
    }

    @PostMapping("/refreshToken")
    public String refreshtoken(String refershToken) {
        TokenModel tokenModel = JwtTokenUtil.token2tokenModal(refershToken);
        if (tokenModel == null )
            throw new BusinessException("000","请重新登录");
        String userId = tokenModel.getUser().getId()+"";
        TokenModel tokenModelnew =  (TokenModel)redisService.getFromMapCache("user-auth-"+userId,userId);
        tokenModelnew.setToken(JwtTokenUtil.generateToken(tokenModel.getUser(),"access_token"));
        int seconds = 3600*24*7;
        redisService.putMapCache("user-auth-"+userId,userId,tokenModelnew,seconds);
        return tokenModelnew.getToken();
    }

    /**
     * 注册
     * @return
     */

    @PostMapping("/register")
    public TokenModel register(@RequestBody UserInfoModel model){
        model.setPasswd(MD5.MD5Encode(model.getPasswd()));
        UserInfoModel userInfoModel = userService.saveUser(model);
        UserAuthorityVO user = userService.getUserAuthority(userInfoModel.getAccount());
        TokenModel tokenModel = new TokenModel();
        tokenModel.setToken(JwtTokenUtil.generateToken(user,"access_token"));
        tokenModel.setRefreshToken(JwtTokenUtil.generateToken(user,"refresh_token"));
        tokenModel.setUser(user);
        int seconds = 3600*24*7;
        redisService.putMapCache("user-auth-"+user.getId(),user.getId().toString(),tokenModel,seconds);
        return tokenModel;
    }

}
