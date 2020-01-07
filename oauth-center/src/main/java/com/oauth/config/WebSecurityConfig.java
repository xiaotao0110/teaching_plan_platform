package com.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 配置spring security
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 开启密码授权
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private UserDetailsService authUserDetailsService;

    /**
     * 用户身份验证
     * 实现UserDetailsService此类的loadUserByUsername方法
     */
    @Bean
    public UserDetailsService userDetailsService() { //主要是配置这个Bean，用于授权服务器配置中注入
        return super.userDetailsService();
    }

    /**
     * 加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 验证用户身份，此处使用注入的UserDetailsService
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailsService).passwordEncoder(passwordEncoder());
    }


    /**
     * authorizeRequests()配置路径拦截，表明路径访问所对应的权限，角色，认证信息。
     * formLogin()对应表单认证相关的配置
     * logout()对应了注销相关的配置
     * httpBasic()可以配置basic登录
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http
                .authorizeRequests()
                //列出路径不需要权限
                .antMatchers("/resources/**", "/templates/**", "/static/**",
                        "/css/**", "/fonts/**", "/i/**", "/images/**", "/js/**", "/**/*.ico", "/**/*.html").permitAll()
                //拥有ROLE_ADMIN或ROLE_SYSTEM角色
                //.antMatchers("/xxx/**").hasAuthority("admin")
                //拥有admin权限
                //.antMatchers("/xxx/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYSTEM')")
                //所有请求都需要通过认证
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //需要username和password
                //.usernameParameter("username")
                //.passwordParameter("password")
                .failureForwardUrl("/login?error")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable(); //关跨域保护
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/fonts/**");
        web.ignoring().antMatchers("/i/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/**/*.html");
        web.ignoring().antMatchers("/**/*.ico");
    }

}