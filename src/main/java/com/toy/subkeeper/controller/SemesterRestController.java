package com.toy.subkeeper.controller;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.SemesterDto;
import com.toy.subkeeper.security.PrincipalDetails;
import com.toy.subkeeper.service.Impl.SemesterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/semester")

public class SemesterRestController {
    private final SemesterServiceImpl semesterService;

    public Long getUserId(PrincipalDetails principalDetails) {
        if(principalDetails != null && principalDetails.getUser() != null) {
            return principalDetails.getUser().getId();
        }
        return null;
    }

    @PostMapping()
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody SemesterDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(semesterService.create(param, getUserId(principalDetails)));
    }

    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody SemesterDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        semesterService.update(param, getUserId(principalDetails));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody SemesterDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        semesterService.delete(param, getUserId(principalDetails));

        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<SemesterDto.DetailResDto> detail(DefaultDto.DetailReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(semesterService.detail(param, getUserId(principalDetails)));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SemesterDto.DetailResDto>> list(SemesterDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(semesterService.list(param, getUserId(principalDetails)));
    }
}
