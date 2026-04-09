package com.toy.subkeeper.controller;

import com.toy.subkeeper.exception.InvalidTokenException;
import com.toy.subkeeper.security.AuthService;
import com.toy.subkeeper.security.ExternalProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthRestController {

    final ExternalProperties externalProperties;
    final AuthService authService;

    @PostMapping("")
    public ResponseEntity<Void> access(HttpServletRequest request){
        String accessToken = null;
        String prefix = externalProperties.getTokenPrefix();
        String refreshToken = request.getHeader(externalProperties.getRefreshKey());
        if(refreshToken == null || !refreshToken.startsWith(prefix)){
            throw new InvalidTokenException("no prefix");
        }
        refreshToken = refreshToken.substring(prefix.length());
        accessToken = authService.issueAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).header(externalProperties.getAccessKey(), prefix + accessToken).build();
    }

}
