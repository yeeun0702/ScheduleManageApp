package com.example.schedulemanageapp.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank @Size(max = 4, message = "최대 4글자까지 가능합니다.")
        String userName,

        @NotBlank(message = "이메일은 필수입니다.")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "올바른 이메일 형식이 아닙니다."
        )
        String email,

        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        String password
) {
}
