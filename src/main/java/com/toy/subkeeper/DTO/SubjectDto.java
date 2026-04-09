package com.toy.subkeeper.DTO;

import com.toy.subkeeper.domain.Subject;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

public class SubjectDto {
    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class CreateReqDto {
        @NotNull(message = "과목명 필수")
        @Size(max = 50)
        String subjectName;

        Long semesterId;

        public Subject toEntity(){
            return Subject.of(getSemesterId(), getSubjectName());
        }
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class UpdateReqDto extends DefaultDto.UpdateReqDto {
        String subjectName;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class DetailResDto extends DefaultDto.DetailResDto {
        String subjectName;

        Long semesterId;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class ListReqDto extends DefaultDto.ListReqDto {
        String subjectName;
    }
}
