package com.example.schedulemanageapp.domain.schedule.dto.response;

import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleDetailResponseDto(
        Long scheduleId,
        String todoTitle,
        String todoContent,
        String userName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ScheduleDetailResponseDto from(Schedule schedule) {
        return ScheduleDetailResponseDto.builder()
                .scheduleId(schedule.getScheduleId())
                .todoTitle(schedule.getTodoTitle())
                .todoContent(schedule.getTodoContent())
                .userName(schedule.getUsers().getUserName()) // Users 엔티티에서 가져옴
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
}
