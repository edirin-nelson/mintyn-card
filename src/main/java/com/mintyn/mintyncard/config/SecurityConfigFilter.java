package com.mintyn.mintyncard.config;

import com.mintyn.mintyncard.security.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigFilter {

    private final LogoutHandler logoutHandler;
    private final JWTAuthenticationFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;


    private final String[] WHITE_LIST = new String[]{
            "/api/v1/card/**"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors((cors)->cors.disable())
                .csrf((csrf)-> csrf.disable())

                // this permit all endpoints having the prefix "/api/v1/auth/" without authentication
                .authorizeHttpRequests((authorize)-> authorize.requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .requestMatchers(WHITE_LIST)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement((session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // this signout the customer once the "/api/v1/logout" endpoint is called
                .logout((logout)->logout.logoutUrl("/api/v1/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .build();
    }

}
