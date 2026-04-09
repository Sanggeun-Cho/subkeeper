package com.toy.subkeeper.mapper;

import com.toy.subkeeper.DTO.UserDto;

import java.util.List;

public interface UserMapper {
    UserDto.DetailResDto detail(Long id);
    List<UserDto.DetailResDto> list(UserDto.ListReqDto param);
}
