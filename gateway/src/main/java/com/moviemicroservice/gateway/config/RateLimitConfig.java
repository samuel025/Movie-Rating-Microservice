package com.moviemicroservice.gateway.config;


import com.moviemicroservice.gateway.Security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class RateLimitConfig {
    private final JWTService jwtService;

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try{
                    String userId = jwtService.extractUserId(token);
                    return Mono.just("user:" + userId);
                } catch (Exception e) {
                    String ipAddress = getClientIP(exchange);
                    return Mono.just("ip:" + ipAddress);
                }
            }
            String ipAddress = getClientIP(exchange);
            return Mono.just("ip:" + ipAddress);
        };
    }

    private String getClientIP(ServerWebExchange exchange) {
        if (exchange.getRequest().getRemoteAddress() != null) {
            return exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        }
        return "unknown";
    }

}
