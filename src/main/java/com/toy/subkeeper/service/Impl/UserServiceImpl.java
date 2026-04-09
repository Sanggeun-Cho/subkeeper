package com.toy.subkeeper.service.Impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.UserDto;
import com.toy.subkeeper.mapper.UserMapper;
import com.toy.subkeeper.repository.SemesterRepository;
import com.toy.subkeeper.domain.User;
import com.toy.subkeeper.repository.UserRepository;
import com.toy.subkeeper.security.AuthService;
import com.toy.subkeeper.security.ExternalProperties;
import com.toy.subkeeper.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SemesterRepository semesterRepository;
    final ExternalProperties externalProperties;
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Override
    public String google(String idTokenString) {
        //
        log.info("Received google id token: {}", idTokenString);

        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(externalProperties.getGoogleClientId()))
                .build();

        String googleSub = null;
        String name = null;
        String email = null;

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if(idToken == null) {
                throw new RuntimeException("구글아이디 토큰을 찾을 수 없습니다");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            googleSub = payload.getSubject();
            name = (String) payload.get("name");
            email = payload.getEmail();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(googleSub == null) {
            throw new RuntimeException("구글 정보를 찾지 못했습니다");
        }

        String userName = googleSub;
        Long id = null;
        User user = userRepository.findByUserName(userName);
        if(user == null){
            // 첫 회원인 경우
            String password = UUID.randomUUID().toString()
                    .replace("-", "")
                    .substring(0, 16);

            DefaultDto.CreateResDto res = create(UserDto.CreateReqDto.builder()
                    .email(email)
                    .userName(userName)
                    .password(password)
                    .name(name)
                    .build(), (long) -200);

            id = res.getId();
        } else {
            id = user.getId();
        }

        //
        log.info("Google id token: {}", id);

        return authService.createRefreshToken(id);
    }

    @Override
    public DefaultDto.CreateResDto create(UserDto.CreateReqDto param, Long reqUserId) {
        User user = userRepository.findByUserName(param.getUserName());

        if(user != null){
            throw new RuntimeException("이미 존재하는 유저입니다.");
        }

        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));

        User newUser = userRepository.save(param.toEntity());
        return newUser.toCreateResDto();
    }

    @Override
    public void update(UserDto.UpdateReqDto param, Long reqUserId){
        User user = userRepository.findById(param.getId())
                .orElseThrow(() -> new RuntimeException("데이터가 없습니다"));

        user.update(param);
        userRepository.save(user);
    }

    @Override
    public void delete(DefaultDto.UpdateReqDto param, Long reqUserId){
        update(UserDto.UpdateReqDto.builder()
                .id(param.getId())
                .deleted(true)
                .build(), reqUserId);
    }

    public UserDto.DetailResDto get(DefaultDto.DetailReqDto param, Long reqUserId) {
        return userMapper.detail(param.getId());
    }

    @Override
    public UserDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId) {
        //본인 정보인 경우 확인
        if(param.getId() == null || param.getId() == 0){ param.setId(reqUserId); }
        return get(param, reqUserId);
    }

    @Override
    public List<UserDto.DetailResDto> list(UserDto.ListReqDto param, Long reqUserId) {
        return addList(userMapper.list(param),reqUserId);
    }

    public List<UserDto.DetailResDto> addList(List<UserDto.DetailResDto> list, Long reqUserId){
        List<UserDto.DetailResDto> newList = new ArrayList<>();
        for(UserDto.DetailResDto each : list){
            newList.add(get(DefaultDto.DetailReqDto.builder().id(each.getId()).build(), reqUserId));
        }
        return newList;
    }
}
