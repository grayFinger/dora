//package com.dora.commonservice.config;
//
//
//import com.dora.commonservice.interceptor.CustomAuthenticationFilter;
//import com.dora.commonservice.interceptor.CustomAuthorizationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import static org.springframework.http.HttpMethod.GET;
//import static org.springframework.http.HttpMethod.POST;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled=true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final UserDetailsService userDetailsService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
////NOTE：
//// 可以在这个的方使用不同的用户认证方法，可以连接数据库， 也可以作用InMemoryDatabase,等不同年度计划工
////        可以查看auth中不同的方法。
//// NOTE：
////        必须对密码进行加密处理， 这是security的要求。我们在程序中需要建立一个可以实例化的passwordEncoder,
////        所以我们需要建立一个Bean 这样每次启动程序时，就可以实例化了
////        我们将这个Bean可以写在主程序中。
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
//
//    /**
//     * NOTE： 以前可以使用在服务器上的会话保存的记录来验证用户，这是一种有状态的处理方法。Cookie和SessionId
//     * NOTE： 现在我们使用JWT 进行身份认证。 用户认证时，给用户颁发一个Token，而不使用Cookie的方式.需要在这个方法中进行设置
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 我们在这里更改默认的登录地址
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
//        customAuthenticationFilter.setFilterProcessesUrl("/api/common");
//
//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeRequests()
//                .antMatchers("/api/**").permitAll();
//        // 服务端无状态
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        //NOTE： 以下的代码顺序是很重要的
//        //允许任务没有经过认证的用户访问这个地址。
//        // 这个地址/login并不是我们在controller中的定义的。而是在UsernamePasswordAuthenticationFilter中定义的。 去检查源码
//        // 经过上面的代码后， 登录的地址已经变成 /api/login
//        // 需要允许刷新
//        http.authorizeRequests().antMatchers("/api/login/**","/api/token/refresh").permitAll();
//
//        //我们可以在这个地方为不同的地址设置没的角色配置信息，可以设置多个
//        http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAnyAuthority("ROLE_USER");
//        http.authorizeRequests().antMatchers(POST,"/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
//
//        //我们也可以使用IP地址或多个角色对请求进行控制。
////        http.authorizeRequests().antMatchers(POST,"/api/user/save/**").hasAnyRole("ROLE_ADMIN");
////        http.authorizeRequests().antMatchers(POST,"/api/user/save/**").hasIpAddress("192.168.1.100");
//
//
//
//        // 允许任务的请求
//        //如果使用permitAll相当于没有用户身份的认证
//        // http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests().anyRequest().authenticated();
//
//        //现在添加过滤器
//        //http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean())); //使用默认的登录地址
//        http.addFilter(customAuthenticationFilter); //使用变更后的登录地址。
//
//        //我们需要将这个Filter加在任何AuthenticationFilter前面，因为我们需要确保拦截每个请求
//        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    }
//
//    //获取AuthenticationManager
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//}
