package com.example.schedulemanageapp.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserDeleteRequestDto(
        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        String password
) {
}
