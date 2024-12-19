package com.project.eventsapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
        @Autowired
        private UserDetailsService uds;

        @Autowired
        private BCryptPasswordEncoder encoder;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/signup",
                                                                "/css/**",
                                                                "/components/**",
                                                                "/js/**",
                                                                "/vendor/**",
                                                                "/font/**",
                                                                "/img/**")
                                                .permitAll()
                                                .anyRequest().authenticated())

                                .formLogin((form) -> form
                                                .loginPage("/login")
                                                .permitAll()
                                                .successHandler((request, response, authentication) -> {
                                                        var savedRequest = (org.springframework.security.web.savedrequest.DefaultSavedRequest) request
                                                                        .getSession()
                                                                        .getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                                                        if (savedRequest != null) {
                                                                response.sendRedirect(savedRequest.getRequestURL());
                                                        } else {
                                                                response.sendRedirect("/");
                                                        }
                                                }))
                                .exceptionHandling((exceptionHandling) -> exceptionHandling
                                                .accessDeniedPage("/access-denied"))
                                .logout((logout) -> logout.permitAll());
                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                UserDetails user = User.withUsername("admin")
                                .password("{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG")
                                .roles("ADMIN")
                                .build();
                return new InMemoryUserDetailsManager(user);
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
                authenticationProvider.setUserDetailsService(uds);
                authenticationProvider.setPasswordEncoder(encoder);
                return authenticationProvider;
        }

}