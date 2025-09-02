//package com.moviemicroservice.gateway.config;
//
//
//import com.moviemicroservice.gateway.Security.JWTFilter;
//import com.moviemicroservice.gateway.Security.JWTWebFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
//public class SecurityConfig {
//
//    @Autowired
//    JWTWebFilter JwtWebFilter;
//
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
//                                                            JWTWebFilter jwtWebFilter) {
//        return http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers("/api/v1/auth/**").permitAll()   // permit auth endpoints
//                        .anyExchange().authenticated()          // secure everything else
//                )
//                .addFilterAt(jwtWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
//                .build();
//    }
//}


package com.moviemicroservice.gateway.config;

import com.moviemicroservice.gateway.Security.JWTService;
import com.moviemicroservice.gateway.Security.JWTWebFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    JWTService jwtService;

    public SecurityConfig(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    // Filter chain for public endpoints (no JWT filter)
    @Bean
    @Order(1)
    public SecurityWebFilterChain publicSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .securityMatcher(ServerWebExchangeMatchers.matchers(
                        // All auth endpoints
                        ServerWebExchangeMatchers.pathMatchers("/api/v1/auth/**"),
                        // GET-only movie endpoints
                        ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET,
                                "/api/v1/movies/**"
                        )
                ))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .anyExchange().permitAll()
                )
                .build();
    }

    // Filter chain for protected endpoints (with JWT filter)

    @Bean
    @Order(2)
    public SecurityWebFilterChain protectedSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .anyExchange().authenticated()
                )
                .addFilterAt(new JWTWebFilter(jwtService), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();

    }

}
