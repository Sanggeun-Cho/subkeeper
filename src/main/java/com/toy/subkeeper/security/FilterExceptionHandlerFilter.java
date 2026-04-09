package com.toy.subkeeper.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FilterExceptionHandlerFilter extends OncePerRequestFilter {
    /**
     * TokenExpiredException 핸들링을 위한 필터
     * 상태코드 UNAUTHORIZED(401)을 response에 담아 리턴
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try {
                response.getWriter().write("Invalid Access Token");
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }
}
