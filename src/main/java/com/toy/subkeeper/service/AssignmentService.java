package com.toy.subkeeper.service;

import com.toy.subkeeper.DTO.AssignmentDto;
import com.toy.subkeeper.DTO.DefaultDto;

import java.util.List;

public interface AssignmentService {
    DefaultDto.CreateResDto create(AssignmentDto.CreateReqDto param, Long reqUserId);

    void update(AssignmentDto.UpdateReqDto param, Long reqUserId);
    void delete(AssignmentDto.UpdateReqDto param, Long reqUserId);

    AssignmentDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId);

    List<AssignmentDto.DetailResDto> list(AssignmentDto.ListReqDto param, Long reqUserId);
}
