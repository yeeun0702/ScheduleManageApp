package com.example.schedulemanageapp.common.exception.base;

import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
