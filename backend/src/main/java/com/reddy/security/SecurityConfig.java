package com.reddy.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> {
                    var cors = new org.springframework.web.cors.CorsConfiguration();
                    cors.setAllowedOrigins(java.util.List.of("*"));
                    cors.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    cors.setAllowedHeaders(java.util.List.of("*"));
                    return cors;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.

                                requestMatchers("/api/auth/login","/error")
                                .permitAll()
                                .requestMatchers("/api/forget-password/**")
                                .permitAll()
                                .requestMatchers("/api/auth/me").authenticated()
                                .requestMatchers( "api/auth/change-password").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/employees").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/employees/current").hasAnyAuthority("ADMIN", "MANAGER", "EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/api/employees/**").hasAnyAuthority("ADMIN", "MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/departments").hasAnyAuthority("ADMIN", "MANAGER", "EMPLOYEE")
                                .requestMatchers(HttpMethod.POST, "/api/departments").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/departments/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/departments/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/departments/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/designations/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/designations").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/designations/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/designations/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/leaves/apply/**").hasAuthority("EMPLOYEE")
                                .requestMatchers(HttpMethod.POST, "/api/leaves/types/**","/error").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/leaves/my-leaves").hasAuthority("EMPLOYEE")
//                                .requestMatchers(HttpMethod.GET, "/api/leaves").hasAnyAuthority("MANAGER", "ADMIN")
//                                .requestMatchers(HttpMethod.PUT, "/api/leaves/**/approve").hasAnyAuthority("MANAGER", "ADMIN")
//                                .requestMatchers(HttpMethod.PUT, "/api/leaves/**/reject").hasAnyAuthority("MANAGER", "ADMIN")
//                                .requestMatchers(HttpMethod.POST, "/api/tasks/assign").hasAnyAuthority("MANAGER")
//                                .requestMatchers(HttpMethod.GET, "/api/tasks/my-tasks").hasAuthority("EMPLOYEE")
//                                .requestMatchers(HttpMethod.GET, "/api/tasks").hasAnyAuthority("MANAGER", "ADMIN")
//                                .requestMatchers(HttpMethod.PUT, "/api/tasks/**/feedback").hasAuthority("MANAGER")
//                                .requestMatchers(HttpMethod.PUT, "/api/tasks/**/complete").hasAuthority("EMPLOYEE")
                                .anyRequest()
                                .authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
