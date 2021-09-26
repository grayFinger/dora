package com.dora.commonservice.controller;

import com.dora.commonservice.annotation.LoginRequired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: AuthController
 * @Author: zhaolianqi
 * @Date: 2021/8/23 11:41
 * @Version: v1.0
 */
@RefreshScope
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value(value = "${counter:0}")
    private Integer counter;

    @GetMapping("/test")
    public String test(){
        return counter+"";
    }

    @LoginRequired
    @GetMapping("/key-{key}")
    public String getAuth(@PathVariable String key){
        return "key: " + key;
    }

}
