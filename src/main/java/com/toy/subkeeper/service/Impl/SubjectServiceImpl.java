package com.toy.subkeeper.service.Impl;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.SubjectDto;
import com.toy.subkeeper.exception.DuplicateSubNameException;
import com.toy.subkeeper.domain.Semester;
import com.toy.subkeeper.mapper.SubjectMapper;
import com.toy.subkeeper.repository.SemesterRepository;
import com.toy.subkeeper.domain.Subject;
import com.toy.subkeeper.repository.SubjectRepository;
import com.toy.subkeeper.service.SubjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectMapper subjectMapper;

    // 과목 생성
    @Override
    public DefaultDto.CreateResDto create(SubjectDto.CreateReqDto param, Long reqUserId) {
        String subjectName = param.getSubjectName().trim();

        param.setSubjectName(subjectName);

        if(subjectMapper.existsBySemesterIdAndSubjectName(param, reqUserId)){
            throw new DuplicateSubNameException("동일 학기 내 이미 존재하는 과목입니다: " + subjectName);
        }

        return subjectRepository.save(param.toEntity()).toCreateResDto();
    }

    // 과목 수정
    @Override
    public void update(SubjectDto.UpdateReqDto param, Long reqUserId) {
        Subject subject = subjectRepository.findById(param.getId())
                .orElseThrow(() -> new RuntimeException("데이터가 없습니다"));

        subject.update(param);
        subjectRepository.save(subject);
    }

    // 과목 삭제
    @Override
    public void delete(SubjectDto.UpdateReqDto param, Long reqUserId) {
        update(SubjectDto.UpdateReqDto.builder()
                .id(param.getId())
                .deleted(true)
                .build(), reqUserId);
    }
    
    public SubjectDto.DetailResDto get(DefaultDto.DetailReqDto param, Long reqUserId) {
        return subjectMapper.detail(param.getId());
    }
    
    @Override
    public SubjectDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId) {
        return get(param, reqUserId);
    }

    public List<SubjectDto.DetailResDto> addList(List<SubjectDto.DetailResDto> list, Long reqUserId) {
        List<SubjectDto.DetailResDto> newList = new ArrayList<>();
        for(SubjectDto.DetailResDto subject : list) {
            newList.add(get(DefaultDto.DetailReqDto.builder()
                    .id(subject.getId())
                    .build(), reqUserId));
        }

        return newList;
    }

    @Override
    public List<SubjectDto.DetailResDto> list(SubjectDto.ListReqDto param, Long reqUserId) {
        List<SubjectDto.DetailResDto> idList = subjectMapper.list(param, reqUserId);

        return addList(idList, reqUserId);
    }
}
