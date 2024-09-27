package com.ddk.asmsof306.security;

import com.ddk.asmsof306.config.JwtAuthenticationFilter;
import com.ddk.asmsof306.model.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
public class SecurityAdminConfig {
    @Autowired
    private HttpSession session;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private LogoutHandler logoutHandler;

    @Bean
    SecurityFilterChain securityFilterChainAdmin(HttpSecurity http) throws Exception {
        http
                .csrf(
                        httpSecurityCsrfConfigurer -> {
                            httpSecurityCsrfConfigurer.disable();
                        }
                )
                .cors(httpSecurityCorsConfigurer -> {
                    httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());

                })
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers(HttpMethod.PUT,"/admin/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/admin/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/admin/**").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority("ADMIN","STAFF")
                            .requestMatchers("/user/**").hasAuthority("USER")
                            .requestMatchers("/**").permitAll()
                            .anyRequest().authenticated();

                })
                .sessionManagement(sessionMnagement ->{
                    sessionMnagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin(httpSecurityFormLoginConfigurer -> {
//                    httpSecurityFormLoginConfigurer.loginPage("/admin/login")
//                            .loginProcessingUrl("/admin/login/process")
//                            .successHandler((request, response, authentication) -> {
//                                Account user = (Account) authentication.getPrincipal();
//                                if(user.getAuthorities().stream().anyMatch(er -> er.getAuthority().equals("ADMIN"))){
//                                    response.sendRedirect("/admin/");}
//                                else {
//                                    response.sendRedirect("/");
//                                }
//                            })
//                            .permitAll();
//                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutUrl("/admin/action_logout")
                            .deleteCookies("JSESSIOND")
                            .logoutSuccessUrl("/admin/login")
                            .addLogoutHandler(logoutHandler);
                });

        return http.build();
    }
//    @Bean
//    @Order(1)
//    SecurityFilterChain securityFilterChainUser(HttpSecurity htpp) throws Exception {
//        htpp
//                .csrf(httpSecurityCsrfConfigurer -> {
//                    httpSecurityCsrfConfigurer.disable();
//                })
//                .cors(httpSecurityCorsConfigurer -> {
//                    httpSecurityCorsConfigurer.disable();
//                })
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
//                    authorizationManagerRequestMatcherRegistry
//                            .requestMatchers("/user/**").hasAuthority("USER");
//
//                })
//                .formLogin(httpSecurityFormLoginConfigurer -> {
//                    httpSecurityFormLoginConfigurer
//                            .loginPage("/login")
//                            .loginProcessingUrl("/login/process")
//                            .successHandler((request, response, authentication) -> {
//                                Account user = (Account) authentication.getPrincipal();
//
//                                if(user.getAuthorities().stream().anyMatch(er -> er.getAuthority().equals("USER"))){
//
//                                    response.sendRedirect("/");}
//                                else {
//                                    response.sendRedirect("/admin/");
//                                }
//                            })
//                            .permitAll();
//
//                })
//                .logout(httpSecurityLogoutConfigurer -> {
//                    httpSecurityLogoutConfigurer
//                            .logoutUrl("/user/logout")
//                            .deleteCookies("JSESSIONID")
//                            .logoutSuccessUrl("/login");
//                })
//
//        ;
//        return htpp.build();
//    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
