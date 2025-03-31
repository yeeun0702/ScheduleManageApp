package com.example.schedulemanageapp.common.exception.base;

import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
