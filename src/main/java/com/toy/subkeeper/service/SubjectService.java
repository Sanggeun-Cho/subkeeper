package com.toy.subkeeper.service;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.SubjectDto;

import java.util.List;

public interface SubjectService {
    DefaultDto.CreateResDto create(SubjectDto.CreateReqDto param, Long reqUserId);

    void update(SubjectDto.UpdateReqDto param, Long reqUserId);

    void delete(SubjectDto.UpdateReqDto param, Long reqUserId);

    SubjectDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId);

    List<SubjectDto.DetailResDto> list(SubjectDto.ListReqDto param, Long reqUserId);
}
