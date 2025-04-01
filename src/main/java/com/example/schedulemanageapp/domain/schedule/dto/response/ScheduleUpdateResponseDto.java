package com.example.schedulemanageapp.domain.schedule.dto.response;

import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleUpdateResponseDto(
        Long scheduleId,
        String todoTitle,
        String todoContent,
        String userName,
        LocalDateTime updatedAt
) {
    public static ScheduleUpdateResponseDto from(Schedule schedule) {
        return ScheduleUpdateResponseDto.builder()
                .scheduleId(schedule.getScheduleId())
                .todoTitle(schedule.getTodoTitle())
                .todoContent(schedule.getTodoContent())
                .userName(schedule.getUsers().getUserName())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
}