package com.zhao.doraclients.client;

//import com.zhao.common.respvo.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: CommonService
 * @Author: zhaolianqi
 * @Date: 2021/8/26 14:43
 * @Version: v1.0
 */
@FeignClient(name = "common-service")
public interface CommonServiceClient {

    @GetMapping("/api/auth/test")
    String test();

}
