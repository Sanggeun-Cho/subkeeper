package com.toy.subkeeper.domain;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.SemesterDto;
import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter
public class Semester extends AuditingFields{
    Long userId;

    @Column(nullable = false, length = 50)
    private String semesterName;

    protected Semester() {}
    private Semester(Long userId, String semesterName) {
        this.userId = userId;
        this.semesterName = semesterName;
    }

    public static Semester of(Long userId, String semesterName) {
        return new Semester(userId, semesterName);
    }

    public DefaultDto.CreateResDto toCreateResDto() {
        return DefaultDto.CreateResDto.builder()
                .id(getId())
                .build();
    }

    public void update(SemesterDto.UpdateReqDto param){
        if(param.getDeleted() != null) {
            setDeleted(param.getDeleted());
        }
        if(param.getSemesterName() != null) {
            setSemesterName(param.getSemesterName());
        }
    }
}
