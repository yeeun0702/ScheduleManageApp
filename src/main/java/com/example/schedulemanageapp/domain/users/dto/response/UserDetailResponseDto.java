package com.example.schedulemanageapp.domain.users.dto.response;

import com.example.schedulemanageapp.domain.users.entity.Users;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDetailResponseDto(
        Long userId,
        String userName,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserDetailResponseDto from(Users users) {
        return UserDetailResponseDto.builder()
                .userId(users.getUserId())
                .userName(users.getUserName())
                .email(users.getEmail())
                .createdAt(users.getCreatedAt())
                .updatedAt(users.getUpdatedAt())
                .build();
    }
}

