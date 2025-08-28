package com.moviemicroservice.gateway.Security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JWTFilter extends AbstractGatewayFilterFactory<JWTFilter.Config> {

    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();
            log.debug("Processing request for path: {}", path);

            // Allow auth endpoints without token
            if (path.contains("/auth/register") || path.contains("/auth/login")) {
                log.debug("Allowing auth endpoint without token: {}", path);
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            log.debug("Authorization header: {}", authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Missing or invalid Authorization header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            if (!jwtService.isTokenValid(token)) {
                log.warn("Invalid JWT token");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Extract user information from token
            String email = jwtService.extractEmail(token);
            String role = jwtService.extractRole(token);
            log.debug("Valid token for user: {}, role: {}", email, role);

            // Create modified request with user headers
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-User-Email", email)
                    .header("X-User-Role", role)
                    .header(HttpHeaders.AUTHORIZATION, authHeader)
                    .build();

            log.info("Forwarding request with user headers");

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }

    @Data
    public static class Config {
        // Add any configuration properties if needed
        private boolean enabled = true;
    }
}