package com.example.login.Config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        /*  return new SimplePasswordEncoder();*/
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()

                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/status", "/images/**", "/view/join", "/auth/join","/board/list","/board/boardview", "/api/**").permitAll() // '/board/list' 경로 추가
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/view/login")
                        .loginProcessingUrl("/login-process")
                        .usernameParameter("userid")
                        .passwordParameter("pw")
                        .defaultSuccessUrl("/view/dashboard", true)
                        .permitAll()
                )
                .logout(withDefaults());

        return http.build();
    }
}
//로그인 보안 하는 친구