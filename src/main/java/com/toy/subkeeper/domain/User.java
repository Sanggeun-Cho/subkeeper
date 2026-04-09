package com.toy.subkeeper.domain;

import com.toy.subkeeper.DTO.DefaultDto;
import com.toy.subkeeper.DTO.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
public class User extends AuditingFields {
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    private String userName;
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    protected User() {}

    private User(String email, String userName, String password, String name){
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.name = name;
    }

    public static User of (String email, String userName, String password, String name) {
        return new User(email, userName, password, name);
    }

    public DefaultDto.CreateResDto toCreateResDto(){
        return DefaultDto.CreateResDto.builder().id(getId()).build();
    }

    public void update(UserDto.UpdateReqDto param){
        if(param.getDeleted() != null){
            setDeleted(param.getDeleted());
        }
        if(param.getPassword() != null){
            setPassword(param.getPassword());
        }
        if(param.getName() != null){
            setName(param.getName());
        }
    }
}
