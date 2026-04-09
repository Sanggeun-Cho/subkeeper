package com.toy.subkeeper.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 TOKENFACTORY에서 쓰는 임시 코드!!
 */
@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
@SuppressWarnings("serial")
@NoArgsConstructor
public class NoAuthException extends RuntimeException {
    public NoAuthException(String message) {
        super(message);
    }
}