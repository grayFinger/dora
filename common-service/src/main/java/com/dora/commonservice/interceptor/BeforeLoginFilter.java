//package com.dora.commonservice.interceptor;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import java.io.IOException;
//
//
//public class BeforeLoginFilter extends GenericFilterBean {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("This is a filter before UsernamePasswordAuthenticationFilter.");
//        // 继续调用 Filter 链
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/user/**").hasRole("USER")
//                .and()
//                .formLogin().loginPage("/login").defaultSuccessUrl("/user")
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
//
//        // 在 UsernamePasswordAuthenticationFilter 前添加 BeforeLoginFilter
//        http.addFilterBefore(new BeforeLoginFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        // 在 CsrfFilter 后添加 AfterCsrfFilter
//        http.addFilterAfter(new AfterCsrfFilter(), CsrfFilter.class);
//    }
//}
