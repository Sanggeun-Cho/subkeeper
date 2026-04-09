package com.toy.subkeeper.domain;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.SubjectDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Subject extends AuditingFields {
    Long semesterId;

    @Column(nullable = false, length = 50)
    String subjectName;

    protected Subject() {}

    private Subject(Long semesterId, String subjectName) {
        this.semesterId = semesterId;
        this.subjectName = subjectName;
    }

    public static Subject of(Long semesterId, String subjectName) {
        return new Subject(semesterId, subjectName);
    }

    public void update(SubjectDto.UpdateReqDto param) {
        if(param.getDeleted() != null){
            setDeleted(param.getDeleted());
        }
        if(param.getSubjectName() != null){
            setSubjectName(param.getSubjectName());
        }
    }

    public DefaultDto.CreateResDto toCreateResDto() {
        return DefaultDto.CreateResDto.builder()
                .id(getId())
                .build();
    }
}
