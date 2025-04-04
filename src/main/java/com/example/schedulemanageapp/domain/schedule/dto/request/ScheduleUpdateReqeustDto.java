package com.example.schedulemanageapp.domain.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ScheduleUpdateReqeustDto(

        @NotBlank(message = "일정의 제목은 공백일 수 없습니다.") @Size(max = 10, message = "최대 10자까지 가능합니다.")
        String todoTitle,

        @NotBlank(message = "일정의 내용은 공백일 수 없습니다.") @Size(max = 200, message = "최대 200자까지 가능합니다.")
        String todoContent,

        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        String password
) {
}
