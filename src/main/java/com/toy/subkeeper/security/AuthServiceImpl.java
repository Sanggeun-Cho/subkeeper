package com.toy.subkeeper.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.toy.subkeeper.domain.RefreshToken;
import com.toy.subkeeper.exception.InvalidTokenException;
import com.toy.subkeeper.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final ExternalProperties externalProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Algorithm getTokenAlgorithm() {
        return Algorithm.HMAC256(externalProperties.getTokenSecretKey());
    }

    /**
     * Access Token 생성을 위한 함수
     * Payload에 userId를 담는다
     */
    @Override
    public String createAccessToken(Long userId) {
        return JWT.create()
                .withSubject("accessToken")
                .withClaim("id", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + externalProperties.getAccessTokenExpirationTime()))
                .sign(getTokenAlgorithm());
    }

    /**
     * Access Token 검증을 위한 함수
     */
    @Override
    public Long verifyAccessToken(String accessToken) throws JWTVerificationException {
        return JWT.require(getTokenAlgorithm())
                .build()
                .verify(accessToken)
                .getClaim("id").asLong();
    }

    /**
     * Refresh Token 생성을 위한 함수
     * Payload에 userId를 담는다
     * DB에 저장도 가능
     * redis에 userId를 key로, 발급한 Refresh Token을 value로 저장할 수도 있다
     */
    @Override
    public String createRefreshToken(Long userId) {
        // 로그인하면 이전 리프레시 토큰 지우기
        // 접속 가능 기기에 따라 다를듯?
        revokeRefreshToken(userId);

        String refreshToken = JWT.create()
                .withSubject("refreshToken")
                .withClaim("id", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + externalProperties.getRefreshTokenExpirationTime()))
                .sign(getTokenAlgorithm());

        refreshTokenRepository.save(RefreshToken.of(userId, refreshToken));

        return refreshToken;
    }

    @Override
    public void revokeRefreshToken(Long userId) {
        refreshTokenRepository.deleteAll(refreshTokenRepository.findByUserId(userId));
    }

    /**
     * Refresh Token 검증 함수
     */
    @Override
    public Long verifyRefreshToken(String refreshToken) throws JWTVerificationException {
        refreshTokenRepository.findByContent(refreshToken)
                .orElseThrow(() -> new InvalidTokenException(""));

        return JWT.require(getTokenAlgorithm())
                .build()
                .verify(refreshToken)
                .getClaim("id").asLong();
    }

    /**
     * 	Access Token 발급을 위한 함수.
     *  Refresh Token이 유효하다면 Access Token 발급.
     */
    @Override
    public String issueAccessToken(String refreshToken) throws JWTVerificationException {
        Long userId = verifyRefreshToken(refreshToken);

        String accessToken = createAccessToken(userId);

        return accessToken;
    }
}
