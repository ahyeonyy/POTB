package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.Authentication;


import java.io.IOException;

import com.example.demo.dao.AccountDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;

@Setter
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    AccountDAO dao;

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/login", "/main","/image/**","/style/**","/join").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().ignoringRequestMatchers("/join");

        http.formLogin()
                .loginPage("/login").permitAll()
                .successHandler(successHandler);

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login");

        http.httpBasic();
        http.csrf().disable().headers().frameOptions().disable(); // post 
        http.headers().frameOptions().disable();  // Removed one of the .csrf().disable()
        return http.build();
    }

    private AuthenticationSuccessHandler successHandler = new AuthenticationSuccessHandler() {
		

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            HttpSession session = request.getSession();
            System.out.println("이거 동작!");
            String loginId = authentication.getName();
            System.out.println("로그인 아이디 : " + loginId);
            System.out.println("findByAid : " + dao.findByAid(loginId));  // Changed as to dao

            session.setAttribute("a", dao.findByAid(loginId));
            response.sendRedirect("/main");
        }
    };
}
