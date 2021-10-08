//package com.dora.commonservice.service;
//
//import com.dora.commonservice.entity.UserAuthorityVO;
//import com.dora.commonservice.entity.UserInfoModel;
//import com.dora.commonservice.service.userService.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
///**
// * NOTE: 因为我们需要在security中使用数据库中的用户名和密码信息，所以有这个服务实现中，
// * 添加了实现<em>UserDetailsService</>这个接口。
// */
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class UserServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserService userService;
//
//
//    /**
//     * 这是UserDetailService要求的实现的方法，security需要使用这个方法对用户进行认证。
//     *
//     * @param account 需要认证的用户账号
//     * @return
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
//        UserAuthorityVO user = userService.getUserAuthority(account);
//        if (user == null) {
//            // 如果没有数据库中找到，抛出一个异常。
//            log.error("user {} not find in the database", account);
//            throw new UsernameNotFoundException("User not find exception ");
//        } else {
//            //如果找到了用户，写一个日志信息
//            log.info("在数据库中找到了用户: {}", user.getAccount());
//        }
//
//        // 需要返回一个Security使用的UserDetail信息,
//        // 所以我们将数据库中的用户角色信息转换为security需要使用对象
//        // 实际上，authorities可以支持任何类型的GrantedAuthority
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getMenus().forEach(menu ->
//                authorities.add(new SimpleGrantedAuthority("menus_"+menu.getId()))
//        );
//        user.getOperations().forEach(operation ->
//                authorities.add(new SimpleGrantedAuthority("operation_"+operation.getId()))
//        );
//        user.getPageElements().forEach(element ->
//                authorities.add(new SimpleGrantedAuthority("element_"+element.getId()))
//        );
//        // 需要返回一个Security使用的UserDetail信息,
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPasswd(),
//                authorities
//        );
//    }
//}
