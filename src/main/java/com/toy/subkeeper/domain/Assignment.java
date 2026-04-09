package com.toy.subkeeper.domain;

import com.toy.subkeeper.DTO.AssignmentDto;
import com.toy.subkeeper.DTO.DefaultDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Getter @Setter @Builder
public class Assignment extends AuditingFields {
    Long subjectId;

    @Column(nullable = false, length = 50)
    String assignmentName;

    @Column(nullable = false)
    LocalDate dueDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    AssignmentCategory category; // ASSIGNMENT, LECTURE, TODO

    @Column(nullable = false)
    boolean isComplete; // ture : 완료, false : 미완료

    protected Assignment() {}
    private Assignment(Long subjectId, String assignmentName, LocalDate dueDate, AssignmentCategory category, boolean isComplete) {
        this.subjectId = subjectId;
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
        this.category = category;
        this.isComplete = isComplete;
    }

    public static Assignment of(Long subjectId, String assignmentName, LocalDate dueDate, AssignmentCategory category, boolean isComplete) {
        return new Assignment(subjectId, assignmentName, dueDate, category, isComplete);
    }

    public void update(AssignmentDto.UpdateReqDto param){
        if(param.getDeleted() != null){
            setDeleted(param.getDeleted());
        }
        if(param.getAssignmentName() != null){
            setAssignmentName(param.getAssignmentName());
        }
        if(param.getDueDate() != null){
            setDueDate(param.getDueDate());
        }
        if(param.getCategory() != null){
            setCategory(param.getCategory());
        }
        if(param.getIsComplete() != null){
            setComplete(param.getIsComplete());
        }
        if(param.getSubjectId() != null){
            setSubjectId(param.getSubjectId());
        }
    }

    public DefaultDto.CreateResDto toCreateResDto(){
        return DefaultDto.CreateResDto.builder()
                .id(getId())
                .build();
    }
}
