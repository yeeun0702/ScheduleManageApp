package com.example.schedulemanageapp.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentUpdateRequestDto(

        @NotBlank @Size(max = 20, message = "최대 20글자까지 가능합니다.")
        String commentContent
) {
}
