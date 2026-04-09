package com.toy.subkeeper.service.Impl;

import com.toy.subkeeper.DTO.AssignmentDto;
import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.domain.Assignment;
import com.toy.subkeeper.mapper.AssignmentMapper;
import com.toy.subkeeper.repository.AssignmentRepository;
import com.toy.subkeeper.domain.Subject;
import com.toy.subkeeper.repository.SubjectRepository;
import com.toy.subkeeper.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;

    // 과제 생성
    @Override
    public DefaultDto.CreateResDto create(AssignmentDto.CreateReqDto param, Long reqUserId){
        return assignmentRepository.save(param.toEntity()).toCreateResDto();
    }

    // 과제 수정
    @Override
    public void update(AssignmentDto.UpdateReqDto param, Long reqUserId){
        Assignment assignment = assignmentRepository.findById(param.getId())
                .orElseThrow(() -> new RuntimeException("데이터가 존재하지 않습니다."));

        assignment.update(param);
        assignmentRepository.save(assignment);
    }

    // 과제 삭제
    @Override
    public void delete(AssignmentDto.UpdateReqDto param, Long reqUserId){
        update(AssignmentDto.UpdateReqDto.builder()
                .id(param.getId())
                .deleted(true)
                .build(), reqUserId);
    }

    public AssignmentDto.DetailResDto get(DefaultDto.DetailReqDto param){
        return assignmentMapper.detail(param.getId());
    }

    @Override
    public AssignmentDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId) {
        return get(param);
    }

    public List<AssignmentDto.DetailResDto> addList(List<AssignmentDto.DetailResDto> list){
        List<AssignmentDto.DetailResDto> newList = new ArrayList<>();
        for(AssignmentDto.DetailResDto assignment : list) {
            newList.add(get(DefaultDto.DetailReqDto.builder()
                    .id(assignment.getId())
                    .build()));
        }

        return newList;
    }

    @Override
    public List<AssignmentDto.DetailResDto> list(AssignmentDto.ListReqDto param, Long reqUserId) {
        List<AssignmentDto.DetailResDto> idList = assignmentMapper.list(param, reqUserId);

        return addList(idList);
    }
}
