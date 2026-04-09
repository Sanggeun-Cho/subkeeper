package com.toy.subkeeper.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class RefreshToken extends AuditingFields {
    @Setter
    Long userId;

    @Setter
    String content;

    protected RefreshToken(){}
    private RefreshToken(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public static RefreshToken of(Long userId, String content) {
        return new RefreshToken(userId, content);
    }
}
