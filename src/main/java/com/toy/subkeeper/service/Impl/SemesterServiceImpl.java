package com.toy.subkeeper.service.Impl;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.SemesterDto;
import com.toy.subkeeper.exception.NoMatchingDataException;
import com.toy.subkeeper.mapper.SemesterMapper;
import com.toy.subkeeper.exception.DuplicateSemNameException;
import com.toy.subkeeper.domain.Semester;
import com.toy.subkeeper.repository.SemesterRepository;
import com.toy.subkeeper.service.SemesterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    // 학기 생성
    @Override
    public DefaultDto.CreateResDto create(SemesterDto.CreateReqDto param, Long reqUserId) {
        if(param.getUserId() == null || param.getUserId() <= (long) 0){
            param.setUserId(reqUserId);
        }

        String semesterName = param.getSemesterName().trim();
        if (semesterName.isEmpty()) {
            throw new IllegalArgumentException("학기 이름은 필수입니다.");
        }

        // 동일 사용자 내 중복 확인
        if (semesterMapper.existsByUserIdAndSemName(semesterName, reqUserId)) {
            throw new DuplicateSemNameException("해당 사용자에 이미 존재하는 학기입니다: " + semesterName);
        }

        return semesterRepository.save(param.toEntity()).toCreateResDto();
    }

    // 학기 수정 (학기 이름)
    @Override
    public void update(SemesterDto.UpdateReqDto param, Long reqUserId) {
        Semester semester = semesterRepository.findById(param.getId())
                .orElseThrow(() -> new NoMatchingDataException("no data"));

        semester.update(param);

        semesterRepository.save(semester);
    }

    // 학기 삭제
    @Override
    public void delete(DefaultDto.UpdateReqDto param, Long reqUserId) {
        update(SemesterDto.UpdateReqDto.builder()
                .id(param.getId())
                .deleted(true)
                .build(), reqUserId);
    }

    public SemesterDto.DetailResDto get(DefaultDto.DetailReqDto param, Long reqUserId) {
        return semesterMapper.detail(param.getId());
    }

    // 학기 정보
    @Override
    public SemesterDto.DetailResDto detail(DefaultDto.DetailReqDto param, Long reqUserId) {
        return get(param, reqUserId);
    }

    public List<SemesterDto.DetailResDto> addList(List<SemesterDto.DetailResDto> list, Long reqUserId) {
        List<SemesterDto.DetailResDto> newList = new ArrayList<>();
        for(SemesterDto.DetailResDto semester : list){
            newList.add(get(DefaultDto.DetailReqDto.builder()
                    .id(semester.getId())
                    .build(), reqUserId));
        }

        return newList;
    }

    @Override
    public List<SemesterDto.DetailResDto> list(SemesterDto.ListReqDto param, Long reqUserId) {
        List<SemesterDto.DetailResDto> idList = semesterMapper.list(param, reqUserId);

        return addList(idList, reqUserId);
    }
}
