package com.toy.subkeeper.controller;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.SubjectDto;
import com.toy.subkeeper.domain.Subject;
import com.toy.subkeeper.security.PrincipalDetails;
import com.toy.subkeeper.service.Impl.SubjectServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")

public class SubjectRestController {
    private final SubjectServiceImpl subjectService;

    public Long getUserId(PrincipalDetails principalDetails) {
        if(principalDetails != null && principalDetails.getUser() != null) {
            return principalDetails.getUser().getId();
        }
        return null;
    }

    @PostMapping("")
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody SubjectDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(subjectService.create(param, getUserId(principalDetails)));
    }

    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody SubjectDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        subjectService.update(param, getUserId(principalDetails));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody SubjectDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        subjectService.delete(param, getUserId(principalDetails));

        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<SubjectDto.DetailResDto> detail(DefaultDto.DetailReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(subjectService.detail(param, getUserId(principalDetails)));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SubjectDto.DetailResDto>> list(SubjectDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(subjectService.list(param, getUserId(principalDetails)));
    }
}
