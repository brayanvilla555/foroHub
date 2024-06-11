package com.app.appforo.controller.filter;

import com.app.appforo.persistence.entity.User;
import com.app.appforo.persistence.repository.UserRepository;
import com.app.appforo.services.security.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRespository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ") || !StringUtils.hasText(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }


        String jwt = authHeader.split(" ")[1];

        String subject;

        try {
            subject = jwtService.extraerSubject(jwt);
        }catch (JwtException ex){
            filterChain.doFilter(request, response);
            return;
        }

        User user = userRespository.findByUsername(subject).get();

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                subject, null, user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
