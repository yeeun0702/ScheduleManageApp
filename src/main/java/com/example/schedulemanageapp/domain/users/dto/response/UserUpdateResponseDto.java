package com.example.schedulemanageapp.domain.users.dto.response;

import com.example.schedulemanageapp.domain.users.entity.Users;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserUpdateResponseDto(
        Long userId,
        String userName,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserUpdateResponseDto from(Users users) {
        return UserUpdateResponseDto.builder()
                .userId(users.getUserId())
                .userName(users.getUserName())
                .email(users.getEmail())
                .createdAt(users.getCreatedAt())
                .updatedAt(users.getUpdatedAt())
                .build();
    }
}
