package com.toy.subkeeper.service;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.UserDto;

import java.util.List;

public interface UserService {
    String google(String idTokenString);

    DefaultDto.CreateResDto create(UserDto.CreateReqDto param, Long reqUserId);

    void update(UserDto.UpdateReqDto param, Long reqUserId);

    void delete(DefaultDto.UpdateReqDto param, Long reqUserId);

    UserDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId);

    List<UserDto.DetailResDto> list(UserDto.ListReqDto param, Long reqUserId);
}
