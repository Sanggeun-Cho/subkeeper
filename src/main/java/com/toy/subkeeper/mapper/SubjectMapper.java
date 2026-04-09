package com.toy.subkeeper.mapper;

import com.toy.subkeeper.DTO.SubjectDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectMapper {
    SubjectDto.DetailResDto detail(Long id);
    List<SubjectDto.DetailResDto> list(@Param("req") SubjectDto.ListReqDto param, Long reqUserId);
    boolean existsBySemesterIdAndSubjectName(@Param("req") SubjectDto.CreateReqDto param, Long reqUserId);
}
