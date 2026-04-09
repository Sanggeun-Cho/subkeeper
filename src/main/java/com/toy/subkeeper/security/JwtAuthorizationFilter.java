package com.toy.subkeeper.security;

import com.toy.subkeeper.domain.User;
import com.toy.subkeeper.exception.NoMatchingDataException;
import com.toy.subkeeper.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.function.Supplier;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final ExternalProperties externalProperties;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, AuthService authService
            , ExternalProperties externalProperties
    ) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.authService = authService;
        this.externalProperties = externalProperties;
    }

    /**
     *  권한 인가를 위한 함수.
     *  Access Token을 검증하고 유효하면 Authentication을 직접 생성해 SecurityContextHolder에 넣는다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader(externalProperties.getAccessKey());

        if(jwtHeader == null || !jwtHeader.startsWith(externalProperties.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = jwtHeader.substring(externalProperties.getTokenPrefix().length());

        Long userId = authService.verifyAccessToken(accessToken);

        User userEntity = userRepository.findById(userId).orElseThrow(new Supplier<NoMatchingDataException>() {
            @Override
            public NoMatchingDataException get() {
                return new NoMatchingDataException("id : " + userId);
            }
        });

        PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
