package com.toy.subkeeper.service;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.SemesterDto;

import java.util.List;

public interface SemesterService {
    DefaultDto.CreateResDto create(SemesterDto.CreateReqDto param, Long reqUserId);
    void update(SemesterDto.UpdateReqDto param, Long reqUserId);
    void delete(DefaultDto.UpdateReqDto param, Long reqUserId);
    SemesterDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId);
    List<SemesterDto.DetailResDto> list(SemesterDto.ListReqDto param, Long reqUserId);
}
