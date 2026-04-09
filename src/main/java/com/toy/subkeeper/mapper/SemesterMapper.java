package com.toy.subkeeper.mapper;

import com.toy.subkeeper.DTO.SemesterDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SemesterMapper {
    SemesterDto.DetailResDto detail(Long id);
    List<SemesterDto.DetailResDto> list(@Param("req") SemesterDto.ListReqDto param, Long reqUserId);
    boolean existsByUserIdAndSemName(String semesterName, Long reqUserId);
}
