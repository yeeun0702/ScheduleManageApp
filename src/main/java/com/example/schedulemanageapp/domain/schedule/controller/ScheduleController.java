package com.example.schedulemanageapp.domain.schedule.controller;


import com.example.schedulemanageapp.common.exception.code.enums.SuccessCode;
import com.example.schedulemanageapp.common.response.ApiResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleDeleteRequestDto;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleUpdateReqeustDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.PageResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleDetailResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleListResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleUpdateResponseDto;
import com.example.schedulemanageapp.domain.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<ApiResponseDto<PageResponseDto<ScheduleListResponseDto>>> searchAllSchedules(
            @RequestParam(required = false) String updatedDate,
            @RequestParam(required = false) Long userId,
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success(SuccessCode.SCHEDULE_LIST_SUCCESS, scheduleService.findSchedulesByConditions(updatedDate, userId, pageable), "/api/schedules")
        );
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ApiResponseDto<ScheduleUpdateResponseDto>> updateSchedule(
            @PathVariable final Long scheduleId,
            @RequestBody @Valid final ScheduleUpdateReqeustDto scheduleUpdateReqeustDto
            ){
        return ResponseEntity.ok(ApiResponseDto.success(SuccessCode.SCHEDULE_UPDATE_SUCCESS, scheduleService.updateSchedule(scheduleId, scheduleUpdateReqeustDto),"api/schedules"));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable final long scheduleId,
            @RequestBody @Valid final ScheduleDeleteRequestDto scheduleDeleteRequestDto) {
        scheduleService.deleteSchedule(scheduleId, scheduleDeleteRequestDto);
        return ResponseEntity.noContent().build();
    }
}
