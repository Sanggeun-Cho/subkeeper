package com.toy.subkeeper.util;

import com.toy.subkeeper.domain.RefreshToken;
import com.toy.subkeeper.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class TokenFactory {
    final RefreshTokenRepository refreshTokenRepository;

    static int refreshTokenValidityHour = 12;
    static int accessTokenValidityHour = 1;

    // 공통 토큰 생성
    public String createToken(Long userId, int termHour) {
        LocalDateTime now = LocalDateTime.now();

        now = now.plusHours(termHour);

        String token = null;

        String info = userId + "_" + now;

        try {
            token = AES256Cipher.AES_Encode(null, info);
        } catch (Exception e) {}

        return token;
    }

    // 리프레시 토큰 생성
    public String createRefreshToken(Long userId) {
        return createToken(userId, refreshTokenValidityHour);
    }

    // 엑세스 토큰 생성
    public String createAccessToken(String refreshToken) {
        Long userId = validateToken(refreshToken); // 리프레시 토큰

        RefreshToken entity = refreshTokenRepository.findByContent(refreshToken).orElse(null);

        if(entity == null) {
            return null;
        }

        Long userIdFromToken = entity.getUserId();
        System.out.println("userIdFromToken: " + userIdFromToken);
        if(!userIdFromToken.equals(userId)){
            return null;
        }

        System.out.println("userId: " + userId);
        if(userId == null){
            return null;
        }

        return createToken(userId, accessTokenValidityHour);

//        return createToken(userId, accessTokenValidityTerm);
    }

    public Long validateAccessToken(String token) {
        Long userId = validateToken(token);
        if(userId == null) {
            throw new RuntimeException("please check your Refresh Token");
        }

        return userId;
    }

    // 연습용 Refresh 토큰 복호화
    public Long validateToken(String token) { // userId만 돌려줄거라 Long
        String info = null;

        try {
            info = AES256Cipher.AES_Decode(null, token); // 복호화

            String[] array_info = info.split("_");
            Long userId = Long.parseLong(array_info[0]);

            LocalDateTime now = LocalDateTime.now();
            String due = array_info[1];
            String nowTime = now.toString();

            // 만료 여부 확인용
            String[] tempArray = {due, nowTime}; // sort를 위한 배열
            Arrays.sort(tempArray); // ASC 기본
            // 현재 시간이 앞으로 와야 만료 아님
            if(nowTime.equals(tempArray[0])) {
                return userId;
            }
        } catch (Exception e) {}

        return null;
    }
}
