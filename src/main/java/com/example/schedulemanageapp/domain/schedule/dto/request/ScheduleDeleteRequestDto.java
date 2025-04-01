package com.example.schedulemanageapp.domain.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ScheduleDeleteRequestDto(
        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        String password
) {
}
