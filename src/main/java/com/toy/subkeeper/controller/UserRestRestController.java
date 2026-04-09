package com.toy.subkeeper.controller;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.UserDto;
import com.toy.subkeeper.security.ExternalProperties;
import com.toy.subkeeper.security.PrincipalDetails;
import com.toy.subkeeper.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")

public class UserRestRestController {
    private final UserServiceImpl userService;
    private final ExternalProperties externalProperties;

    public Long getReqUserId(PrincipalDetails principalDetails){
        if(principalDetails == null || principalDetails.getUser() == null || principalDetails.getUser().getId() == null){
            return null;
        }
        return principalDetails.getUser().getId();
    }

    @PostMapping("/google")
    public ResponseEntity<Void> googleLogin(@RequestBody String token) throws Exception {
        String refreshToken = userService.google(token);
        return ResponseEntity.status(HttpStatus.OK).header(externalProperties.getRefreshKey(), externalProperties.getTokenPrefix() + refreshToken).build();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody UserDto.CreateReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        return ResponseEntity.ok(userService.create(params, reqUserId));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody UserDto.UpdateReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        userService.update(params, reqUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody DefaultDto.UpdateReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        userService.delete(params, reqUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("")
    public ResponseEntity<UserDto.DetailResDto> detail(DefaultDto.DetailReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        return ResponseEntity.ok(userService.detail(params, reqUserId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<UserDto.DetailResDto>> list(UserDto.ListReqDto params, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getReqUserId(principalDetails);
        return ResponseEntity.ok(userService.list(params, reqUserId));
    }
}
