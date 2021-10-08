package com.dora.commonservice.service.userService;

import com.dora.commonservice.entity.UserAuthorityVO;
import com.dora.commonservice.entity.UserInfoModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service")
public interface UserService {

    @RequestMapping(value = "/user/getUserByAccount", method = RequestMethod.GET,headers = {"content-type=application/json"})
    UserInfoModel getUser(String account);

    @RequestMapping(value = "/user/register", method = RequestMethod.POST, headers = {"content-type=application/json"})
    UserInfoModel saveUser(UserInfoModel user);

    @RequestMapping(value = "/user/getUserAuthority", method = RequestMethod.GET,headers = {"content-type=application/json"})
    UserAuthorityVO getUserAuthority(@RequestParam("account") String account);

    @RequestMapping(value = "/user/getUserById", method = RequestMethod.GET, headers = {"content-type=application/json"})
    UserInfoModel getUserById(Long id);
}
