package com.example.schedulemanageapp.domain.schedule.controller;


import com.example.schedulemanageapp.common.exception.code.enums.SuccessCode;
import com.example.schedulemanageapp.common.response.ApiResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.schedulemanageapp.domain.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
