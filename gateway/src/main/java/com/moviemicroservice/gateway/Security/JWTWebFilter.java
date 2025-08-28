package com.moviemicroservice.gateway.Security;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

public class JWTWebFilter implements WebFilter {

    private final JWTService jwtService;

    public JWTWebFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtService.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String username = jwtService.extractEmail(token);
        String role = jwtService.extractRole(token);

        // Create authentication object with authorities
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        // Create security context
        SecurityContext context = new SecurityContextImpl(authentication);

        // Add user headers to request
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder -> builder
                        .header("X-Auth-Username", username)
                        .header("X-Auth-Role", role)
                )
                .build();

        // Set security context and continue with request
        return chain.filter(mutatedExchange)
                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
    }
}