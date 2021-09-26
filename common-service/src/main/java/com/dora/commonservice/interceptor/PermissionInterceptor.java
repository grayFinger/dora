package com.dora.commonservice.interceptor;

import com.dora.commonservice.annotation.CommonPath;
import com.dora.commonservice.annotation.LoginRequired;
import com.dora.commonservice.constants.ResponseStatus;
import com.dora.commonservice.constants.SysConstants;
import com.dora.commonservice.entity.TokenModel;
import com.dora.commonservice.exception.BusinessException;
import com.dora.commonservice.service.UserInfo;
import com.dora.commonservice.utils.JwtTokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (HandlerMethod.class == handler.getClass()) {
            HandlerMethod method = (HandlerMethod) handler;

            if (method.hasMethodAnnotation(CommonPath.class))
                return true;

            //类注解权限验证
            if (!classPermissionHandler(method.getBeanType(), request, response))
                return false;

            // 方法注解权限验证
            if (!methodPermissionHandler(method, request, response))
                return false;
        }
        return true;
    }

    /**
     * 验证 类级别 的权限
     * @param c
     * @param request
     * @return
     */
    private boolean classPermissionHandler(Class<?> c, HttpServletRequest request, HttpServletResponse response) {
        if (c.getAnnotation(LoginRequired.class) != null) {
            checkToken(request.getHeader(SysConstants.TOKEN), response);
        }
        return true;
    }

    private UserInfo checkToken(String token, HttpServletResponse response){
        if (StringUtils.isEmpty(token))
            throw new BusinessException(ResponseStatus.NO_PERMISSION);
        TokenModel tokenModel = JwtTokenUtil.token2tokenModal(token);
        if (tokenModel == null)
            throw new BusinessException(ResponseStatus.UNAUTHORIZED);
        // 返回新的token
        if (tokenModel.getToken() != null)
            response.setHeader(SysConstants.TOKEN, tokenModel.getToken());
        return tokenModel.getUser();
    }

    /**
     * 验证 方法级别 的权限
     * @param method
     * @param request
     * @return
     */
    private boolean methodPermissionHandler(HandlerMethod method, HttpServletRequest request, HttpServletResponse response) {
        if (method.hasMethodAnnotation(LoginRequired.class)) {
            checkToken(request.getHeader(SysConstants.TOKEN), response);
        }
        // 功能权限
//        Class<?> claz = method.getBeanType();
//        if (claz.isAnnotationPresent(Auth.class) && method.hasMethodAnnotation(Auth.class)){
//            Auth clasAuth = claz.getAnnotation(Auth.class);
//            Auth methodAuth = method.getMethodAnnotation(Auth.class);
//            int id = clasAuth.id() + methodAuth.id();
//            UserInfo user = checkToken(request.getHeader(SysConstants.TOKEN), response);
//            Object authInfo = cacheService.getFromMapCache("user-auth-" + user.getId(), String.valueOf(id));
//            if (authInfo == null)
//                throw new BusinessException(ResponseStatus.NO_PERMISSION);
//        }
        return true;
    }
}
