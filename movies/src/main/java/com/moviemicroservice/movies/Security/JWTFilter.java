package com.moviemicroservice.movies.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        log.info("Processing request to: {} with auth header: {}", request.getRequestURI(), authHeader != null ? "Present" : "Missing");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (jwtService.validateToken(token)) {
                String email = jwtService.extractEmail(token);
                String role = jwtService.extractRole(token);

                log.info("Token validated. Email: {}, Role: {}", email, role);

                String formattedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority(formattedRole))
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication set successfully");
            } else {
                log.warn("Token validation failed");
            }
        } else {
            log.warn("No valid Authorization header found");
        }

        filterChain.doFilter(request, response);
    }
}
