package com.toy.subkeeper.DTO;

import com.toy.subkeeper.domain.Semester;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

public class SemesterDto {
    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class CreateReqDto {
        Long userId;

        @NotNull(message = "학기명 필수")
        @Size(max = 50)
        private String semesterName;

        public Semester toEntity() {
            return Semester.of(userId, semesterName);
        }
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class UpdateReqDto extends DefaultDto.UpdateReqDto{
        @NotNull(message = "학기명 필수")
        @Size(max = 50)
        String semesterName;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class DetailResDto extends DefaultDto.DetailResDto{
        Long userId;

        String semesterName;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class ListReqDto extends DefaultDto.ListReqDto{
        String semesterName;
    }
}
