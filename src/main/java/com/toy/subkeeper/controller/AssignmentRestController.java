package com.toy.subkeeper.controller;

import com.toy.subkeeper.DTO.AssignmentDto;
import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.security.PrincipalDetails;
import com.toy.subkeeper.service.Impl.AssignmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignment")

public class AssignmentRestController {
    private final AssignmentServiceImpl assignmentService;

    public Long getUserId(PrincipalDetails principalDetails) {
        if(principalDetails != null && principalDetails.getUser() != null) {
            return principalDetails.getUser().getId();
        }
        return null;
    }

    @PostMapping("")
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody AssignmentDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(assignmentService.create(param, getUserId(principalDetails)));
    }

    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody AssignmentDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        assignmentService.update(param, getUserId(principalDetails));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody AssignmentDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        assignmentService.delete(param, getUserId(principalDetails));

        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<AssignmentDto.DetailResDto> detail(DefaultDto.DetailReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(assignmentService.detail(param, getUserId(principalDetails)));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AssignmentDto.DetailResDto>> list(AssignmentDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ResponseEntity.ok(assignmentService.list(param, getUserId(principalDetails)));
    }
}
