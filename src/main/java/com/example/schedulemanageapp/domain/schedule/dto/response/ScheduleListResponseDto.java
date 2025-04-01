package com.example.schedulemanageapp.domain.schedule.dto.response;
import com.example.schedulemanageapp.domain.schedule.entity.Schedule;

public record ScheduleListResponseDto(
        Long scheduleId,
        String todoTitle,
        String todoContent,
        String userName // 사용자 정보 포함
) {
    public static ScheduleListResponseDto from(Schedule schedule) {
        return new ScheduleListResponseDto(
                schedule.getScheduleId(),
                schedule.getTodoTitle(),
                schedule.getTodoContent(),
                schedule.getUsers().getUserName() // Users 엔티티에서 이름 가져오기
        );
    }
}
