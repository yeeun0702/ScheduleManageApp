package com.example.schedulemanageapp.domain.schedule.service;

import com.example.schedulemanageapp.common.exception.base.CustomException;
import com.example.schedulemanageapp.common.exception.base.NotFoundException;
import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleDetailResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleListResponseDto;
import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import com.example.schedulemanageapp.domain.schedule.repository.ScheduleRepository;
import com.example.schedulemanageapp.domain.users.entity.Users;
import com.example.schedulemanageapp.domain.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    /**
     * 일정 생성
     * - 유저 ID를 통해 유저 정보를 조회한 후, 할일 제목과 내용을 포함한 Schedule 엔티티를 생성 및 저장
     * @param scheduleCreateRequestDto 생성 요청 DTO
     */
    @Transactional
    public void createSchedule(ScheduleCreateRequestDto scheduleCreateRequestDto){
        Users user = userRepository.findById(scheduleCreateRequestDto.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = new Schedule(
                scheduleCreateRequestDto.todoTitle(),
                scheduleCreateRequestDto.todoContent(),
                user
        );
        scheduleRepository.save(schedule);
    }


    /**
     * 선택 일정 상세 조회
     * - 일정 ID로 Schedule을 조회하여 DTO로 변환 후 반환
     * @param scheduleId 조회할 일정 ID
     * @return 일정 상세 정보 DTO
     */
    @Transactional
    public ScheduleDetailResponseDto getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(ScheduleDetailResponseDto::from)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHEDULE_NOT_FOUND));
    }

    /**
     * 조건에 따른 전체 일정 목록 조회
     * - userId와 updatedDate를 조건으로 조회 (둘 다 null 가능)
     * - updatedDate는 YYYY-MM-DD 형식으로 입력되어야 함
     * - 수정일 기준 내림차순 정렬
     * @param updatedDateStr 수정일 (String, YYYY-MM-DD)
     * @param userId 유저 ID (nullable)
     * @return 일정 목록 DTO 리스트
     */
    @Transactional
    public List<ScheduleListResponseDto> findSchedulesByConditions(String updatedDateStr, Long userId) {
        LocalDateTime updatedDate = null;
        if (updatedDateStr != null && !updatedDateStr.isBlank()) {
            updatedDate = LocalDate.parse(updatedDateStr).atStartOfDay();
        }

        return scheduleRepository.findSchedulesByConditions(userId, updatedDate).stream()
                .map(ScheduleListResponseDto::from)
                .collect(Collectors.toList());
    }
}
