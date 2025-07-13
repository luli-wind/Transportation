package com.luliwind.service;

import com.luliwind.entity.User;
import com.luliwind.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service // 必须添加此注解
public class AuthorizeService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || "".equals(username)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        User user = userMapper.findUserByUserNameOrEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }


        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("user")
                .build();
    } // 实现关键接口


}