package com.example.schedulemanageapp.domain.schedule.controller;


import com.example.schedulemanageapp.common.exception.code.enums.SuccessCode;
import com.example.schedulemanageapp.common.response.ApiResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleDetailResponseDto;
import com.example.schedulemanageapp.domain.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
