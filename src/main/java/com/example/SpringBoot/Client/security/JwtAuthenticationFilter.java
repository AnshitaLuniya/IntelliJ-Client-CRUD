package com.example.SpringBoot.Client.security;

import com.example.SpringBoot.Client.service.CustomUserDetailsService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        log.debug("Incoming request path: {}", path);

        if (path.startsWith("/api/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")) {
            log.debug("Skipping JWT filter for path: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            log.debug("JWT token extracted from header");

            try{
            if (jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsername(token);
                log.info("JWT token validated successfully for user: {}", username);

                var userDetails = userDetailsService.loadUserByUsername(username);

                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("SecurityContext updated for user: {}", username);
            }else {
                log.warn("Invalid JWT token");
            }
        } catch (Exception ex) {
            log.error("JWT authentication failed: {}", ex.getMessage(), ex);
        }
    } else {
        log.debug("Authorization header missing or does not start with Bearer");
    }


        filterChain.doFilter(request, response);
    }
}