package com.example.schedulemanageapp.domain.schedule.controller;


import com.example.schedulemanageapp.common.exception.code.enums.SuccessCode;
import com.example.schedulemanageapp.common.response.ApiResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleDetailResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleListResponseDto;
import com.example.schedulemanageapp.domain.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<Void>> createSchedule(@RequestBody @Valid final ScheduleCreateRequestDto scheduleCreateRequestDto) {
        scheduleService.createSchedule(scheduleCreateRequestDto);
        return ResponseEntity.status(201)
                .body(ApiResponseDto.success(SuccessCode.SCHEDULE_CREATE_SUCCESS, "/api/schedules"));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ApiResponseDto<ScheduleDetailResponseDto>> getSchedule(@PathVariable final Long scheduleId) {
        return ResponseEntity.ok(ApiResponseDto.success(SuccessCode.SCHEDULE_READ_SUCCESS, scheduleService.getSchedule(scheduleId), "/api/schedules/"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ScheduleListResponseDto>>> searchAllSchedules(
            @RequestParam(required = false) String updatedDate, // 조회 기준이 되는 수정일
            @RequestParam(required = false) Long userId // 조회 대상의 사용자 ID, null 이면 전체 조회
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success(SuccessCode.SCHEDULE_LIST_SUCCESS, scheduleService.findSchedulesByConditions(updatedDate, userId), "/api/schedules/search")
        );
    }
}
