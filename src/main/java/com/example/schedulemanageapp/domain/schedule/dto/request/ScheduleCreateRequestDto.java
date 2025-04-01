package com.example.schedulemanageapp.domain.schedule.dto.request;
import jakarta.validation.constraints.*;

public record ScheduleCreateRequestDto(

        @NotNull Long userId,

        @NotBlank(message = "할 일은 제목은 공백일 수 없습니다.") @Size(max = 10, message = "최대 10자까지 가능합니다.")
        String todoTitle,

        @NotBlank(message = "할 일은 내용은 공백일 수 없습니다.") @Size(max = 200, message = "최대 200자까지 가능합니다.")
        String todoContent,

        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        String password
) {
}
