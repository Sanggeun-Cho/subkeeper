package com.toy.subkeeper.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toy.subkeeper.domain.Assignment;
import com.toy.subkeeper.domain.AssignmentCategory;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

public class AssignmentDto {
    @Getter @Setter @SuperBuilder
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateReqDto {
        @NotBlank(message = "과제명 필수")
        @Size(max = 100, message = "과제명 150자 이하 입력")
        String assignmentName;

        @NotNull(message = "마감일시 필수")
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate dueDate;

        @NotNull(message = "카테고리 필수")
        AssignmentCategory category;

        @NotNull(message = "과목 ID 필수")
        Long subjectId;

        public Assignment toEntity() {
            return Assignment.of(getSubjectId(), getAssignmentName(), getDueDate(), getCategory(), false);
        }
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class UpdateReqDto extends DefaultDto.UpdateReqDto {
        @NotBlank(message = "과제명 필수")
        @Size(max = 100, message = "과제명 150자 이하 입력")
        String assignmentName;

        @NotNull(message = "마감일시 필수")
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate dueDate;

        @NotNull(message = "카테고리 필수")
        AssignmentCategory category;

        Boolean isComplete;

        @NotNull(message = "과목 ID 필수")
        private Long subjectId;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class DetailResDto extends DefaultDto.DetailResDto {
        String assignmentName;
        LocalDate dueDate;
        AssignmentCategory category;
        Boolean isComplete;

        String deadlineStatus;

        Long subjectId;
        String subjectName;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class ListReqDto extends DefaultDto.ListReqDto {
        String subjectName;
        List<AssignmentCategory> categories;
    }
}