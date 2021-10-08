//package com.dora.commonservice.interceptor;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
//import static java.util.Arrays.stream;
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//import static org.springframework.http.HttpStatus.FORBIDDEN;
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//
///**
// * 我们需要用户在每次使用接口时对用户的token进行有效性验证， 所以我们建立这个类进行处理。
// * 需要在这个Filter上扩展OncePerRequestFilter
// * <p>
// * 因为我们使用OncePerRequestFilter,每一次用户的请求都会经过这个Filter处理。
// */
//@Slf4j
//public class CustomAuthorizationFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain) throws ServletException, IOException {
//        //首先检查是否为登录地址， 如果是，我们不做任何事，直接让请求通过
//        //Refresh-token中没有角色信息，也需要通过
//        if (request.getServletPath().equals("/api/common/userAuth/")) {
//            chain.doFilter(request, response);
//        } else {
//            //我们先检查请求中是否有要求的token,
//            // 检查token的开始可以直接判断是否为我们颁发的，如果不是，我们不需要进行更多的工作，直接拒绝
//            String authorizationHeader = request.getHeader(AUTHORIZATION);
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//
//                //因为有可能出错
//                try {
//                    String token = authorizationHeader.substring("Bearer ".length());
//                    //这个地方必须使用与授权完全相同的算法， 应当使用一个工具类完成这个工作，而不是在这里重新写一次。
//                    //因为Token是用这个算法进行签名
//                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//
//                    //建立一个验证器, 并对token进行验证
//                    JWTVerifier verifier = JWT.require(algorithm).build();
//                    DecodedJWT decodedJWT = verifier.verify(token);
//
//                    //在这里已经成功的认证了用户的token， 我们现在取token中的信息
//                    //提取的信息需要与token生成的过程中使用信息相一致。
//                    String username = decodedJWT.getSubject();
//                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
//
//                    //我们将角色信息从数组转换是为了使用security中的数据对象类型， security需要这样的数据类型
//                    //实际上我们也可以继续使用数组
//                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                    stream(roles).forEach(role -> {
//                        authorities.add(new SimpleGrantedAuthority(role));
//                    });
//
//                    //生成一个新的TOKEN， 只有用户名，以及用户的角色信息。
//                    // 在实际 的系统中，如果用户的角色配置信息要求在更改后立即生效的话，这个方式是不满足要求的，因为角色信息是在用户登录时获取的
//                    UsernamePasswordAuthenticationToken authenticationToken =
//                            new UsernamePasswordAuthenticationToken(username, null, authorities);
//
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    //请求还需要继续运行
//                    chain.doFilter(request, response);
//
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                    log.error("Error Logging in : {}", e.getMessage());
//                    response.setHeader("error", e.getMessage());
//                    response.sendError(FORBIDDEN.value());
//                } catch (JWTVerificationException e) {
//
//                    //如果发生错误，返回错误码， 同时将一些错误信息返回
//                    // 以下使用了两种不同的返回数据的方式。
//                    e.printStackTrace();
//                    log.error("Error Login in JWT verification： {}",e.getMessage());
//                    response.setHeader("error", e.getMessage());
//                    response.setStatus(FORBIDDEN.value());
//                    //response.sendError(FORBIDDEN.value()); //禁止请求继续
//                    Map<String, String> error = new HashMap<>();
//                    error.put("error_message", e.getMessage());
//                    response.setContentType(APPLICATION_JSON_VALUE);
//                    new ObjectMapper().writeValue(response.getOutputStream(), error);
//                }
//            }else{
//                //如果没有header, 或为空
//                chain.doFilter(request, response);
//            }
//        }
//    }
//}
