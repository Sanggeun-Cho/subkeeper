package com.toy.subkeeper.mapper;

import com.toy.subkeeper.DTO.AssignmentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssignmentMapper {
    AssignmentDto.DetailResDto detail(Long id);
    List<AssignmentDto.DetailResDto> list(@Param("req") AssignmentDto.ListReqDto param, Long reqUserId);
}
