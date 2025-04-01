package com.example.schedulemanageapp.common.exception.base;

import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}
