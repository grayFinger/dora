//package com.dora.commonservice.interceptor;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//
///**
// * 扩展用户名密码认证过滤器
// */
//@Slf4j
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    /**
//     * 需要使用AuthenticationManager对用户进行认证，所以依赖注入
//     */
//    private final AuthenticationManager authenticationManager;
//
//    /**
//     * 构造函数， 依赖注入
//     * @param authenticationManager
//     */
//    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    /**
//     * 任何时候，用户输入用户名和密码需要认证时
//     * @param request
//     * @param response
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        // 获取用户名
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        log.info("用户名： {}", username);
//        log.info("密码: {}", password);
//
//        //在这里我们只是获取到用户名和密码生成一个Token
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//
//        //调用AuthenticationManager对用户输入的用户名和密码进行验证
//        return authenticationManager.authenticate(authenticationToken);
//    }
//
//    /**
//     * 成功认证的时候， 发送一个access token 和一个refresh token给用户。
//     * @param request
//     * @param response
//     * @param chain
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authentication) throws IOException, ServletException {
//        /**
//         * 在这里添加 Java JWT 3.18.1到POM中
//         * <!-- https://mvnrepository.com/artifact/com.auth0/java-jwt -->
//         * <dependency>
//         *     <groupId>com.auth0</groupId>
//         *     <artifactId>java-jwt</artifactId>
//         *     <version>3.18.1</version>
//         * </dependency>
//         */
//
//        //我们需要定义一个用户， 这个用户是security中定义的用户而不是我们在项目中定义的用户
//        //通过getPrincipal()返回的对象就是用户。
//        User user = (User) authentication.getPrincipal();
//
//        // 现在我们通过用户中的信息生成Token
//        //使用这个方法对token进行签名， 通常这个签名信息需要安全的保存在某个地方而不是写在程序中。
//        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//        String access_token = JWT.create()
//                .withSubject(user.getUsername())    //Subject信息用于保存标识用户的信息，我们使用用户名。
//                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 1000 * 60)) //过期时间， 10分钟
//                .withIssuer(request.getRequestURI().toString()) //签发者
//                //用户的角色和授权
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm); // 数字签名
//
//        //refresh_token不需要角色信息，需要更长一些时间
//        String refresh_token = JWT.create()
//                .withSubject(user.getUsername())    //Subject信息用于保存标识用户的信息，我们使用用户名。
//                .withExpiresAt(new Date(System.currentTimeMillis() + 24*60 * 60 *1000 )) //过期时间， 10分钟
//                .withIssuer(request.getRequestURI().toString()) //签发者
//                .sign(algorithm); // 数字签名
//
//        //在用户认证成功后，会在Header中设置如下的信息。
////        response.setHeader("access_token", access_token);
////        response.setHeader("refresh_token", refresh_token);
//
//        Map<String, String> tokens = new HashMap<>();
//        tokens.put("access_token", access_token);
//        tokens.put("refresh_token", refresh_token);
//
//        //Json对象返回
//        response.setContentType(APPLICATION_JSON_VALUE);
//        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//    }
//
//    /**
//     * 通过可以在这个地方设置一个不成功认证后进行的动作， 例如连续一些错误登录后， 禁止用户再次登录的操作等。
//     * @param request
//     * @param response
//     * @param failed
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed);
//    }
//}
