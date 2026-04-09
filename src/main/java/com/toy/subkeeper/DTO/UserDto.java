package com.toy.subkeeper.DTO;

import com.toy.subkeeper.domain.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

public class UserDto {
    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class CreateReqDto {
        String email;
        String userName;
        String password;
        String name;

        public User toEntity(){
            return User.of(getEmail(), getUserName(), getPassword(), getName());
        }
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class LoginReqDto{
        String userName;
        String password;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class UpdateReqDto extends DefaultDto.UpdateReqDto {
        String password;
        String name;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class DetailResDto extends DefaultDto.DetailResDto {
        String name;
        String email;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class ListReqDto extends DefaultDto.ListReqDto {
        String name;
        String email;
    }
}
