package com.luliwind.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luliwind.entity.RestBean;
import com.luliwind.service.AuthorizeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Autowired
    AuthorizeService authorizeService;

    // 关键1：注入AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(authorizeService) // 直接链式调用
                .passwordEncoder(passwordEncoder());  // 无需.and()
        return builder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        // 关键2：替换formLogin为自定义JSON过滤器
        http.addFilterAt(new AbstractAuthenticationProcessingFilter(
                new AntPathRequestMatcher("/api/auth/login", "POST"), authManager) {

            { // 初始化处理逻辑
                setAuthenticationSuccessHandler((req, res, auth) -> {
                    res.setCharacterEncoding("UTF-8");
                    res.setStatus(HttpStatus.OK.value());
                    res.getWriter().write(JSONObject.toJSONString(RestBean.success("登录成功")));
                });

                setAuthenticationFailureHandler((req, res, ex) -> {
                    res.setCharacterEncoding("UTF-8");
                    res.setStatus(HttpStatus.UNAUTHORIZED.value());
                    res.getWriter().write(JSONObject.toJSONString(RestBean.failure(401, ex.getMessage())));
                });
            }

            // 关键3：解析JSON请求体
            @Override
            public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
                    throws AuthenticationException {

                try {
                    JSONObject json = JSON.parseObject(req.getInputStream(), JSONObject.class);
                    String username = json.getString("username");
                    String password = json.getString("password");
                    return getAuthenticationManager().authenticate(
                            new UsernamePasswordAuthenticationToken(username, password)
                    );
                } catch (IOException e) {
                    throw new AuthenticationServiceException("请求解析失败", e);
                }
            }
        }, UsernamePasswordAuthenticationFilter.class);

        // 核心配置链
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                // 关键4：保留登出配置
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .permitAll()
                        .logoutSuccessHandler((req, res, auth) -> {
                            res.setCharacterEncoding("UTF-8");
                            res.getWriter().write(JSONObject.toJSONString(RestBean.success("登出成功")));
                        })
                )
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint())
                )
                .build();
    }


    /**
     * 跨域配置
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 明确指定前端开发端口（如 5173、8080）
        config.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:8080"
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowCredentials(true); // 允许携带凭证
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }



    //  认证入口点
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    AuthenticationException authException
            ) throws IOException, ServletException {
                // 访问未经认证的资源处理
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("请先登录系统");
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

}
