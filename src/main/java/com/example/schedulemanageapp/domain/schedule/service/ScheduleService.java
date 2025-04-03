package com.example.schedulemanageapp.domain.schedule.service;

import com.example.schedulemanageapp.common.exception.base.CustomException;
import com.example.schedulemanageapp.common.exception.base.NotFoundException;
import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleDeleteRequestDto;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleUpdateReqeustDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleDetailResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleListResponseDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleUpdateResponseDto;
import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import com.example.schedulemanageapp.domain.schedule.repository.ScheduleRepository;
import com.example.schedulemanageapp.domain.users.entity.Users;
import com.example.schedulemanageapp.domain.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public void createSchedule(final ScheduleCreateRequestDto scheduleCreateRequestDto){
        Users user = userRepository.findById(scheduleCreateRequestDto.userId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = new Schedule(
                scheduleCreateRequestDto.todoTitle(),
                scheduleCreateRequestDto.todoContent(),
                scheduleCreateRequestDto.password(),
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
    public ScheduleDetailResponseDto getSchedule(final Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .map(ScheduleDetailResponseDto::from)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHEDULE_NOT_FOUND));
    }

    /**
     * 조건에 따른 일정 목록 페이징 조회
     * - 사용자 ID(userId)와 수정일(updatedDateStr)을 조합하여 조건에 따라 필터링
     * - Pageable을 이용해 페이징 처리
     * - 최신 수정일 기준 내림차순 정렬
     *
     * @param updatedDateStr 수정일 필터 (예: "2024-03-01"), null 또는 빈 값일 수 있음
     * @param userId 필터링할 사용자 ID, null일 경우 전체 사용자 대상으로 조회
     * @param pageable 페이지 번호, 크기, 정렬 기준 등을 포함한 객체
     * @return 조건에 맞는 일정 리스트를 ScheduleListResponseDto 형태로 Page 객체에 담아 반환
     */
    @Transactional
    public Page<ScheduleListResponseDto> findSchedulesByConditions(String updatedDateStr, Long userId, Pageable pageable) {

        // 문자열로 받은 날짜 필터를 LocalDateTime으로 변환
        LocalDateTime updatedDate = null;
        if (updatedDateStr != null && !updatedDateStr.isBlank()) {
            updatedDate = LocalDate.parse(updatedDateStr).atStartOfDay();
        }

        Page<Schedule> schedules;

        // 사용자 ID와 수정일이 모두 주어진 경우 -> 해당 사용자의 일정 중 수정일 이후 데이터만 조회
        if (userId != null && updatedDate != null) {
            schedules = scheduleRepository.findByUsers_UserIdAndUpdatedAtAfter(userId, updatedDate, pageable);

            // 사용자 ID만 주어진 경우 -> 해당 사용자의 전체 일정 조회
        } else if (userId != null) {
            schedules = scheduleRepository.findByUsers_UserId(userId, pageable);

            // 수정일만 주어진 경우 -> 전체 사용자 중 수정일 이후 일정 조회
        } else if (updatedDate != null) {
            schedules = scheduleRepository.findByUpdatedAtAfter(updatedDate, pageable);

            // 아무 조건도 없을 경우 -> 전체 일정 조회
        } else {
            schedules = scheduleRepository.findAll(pageable);
        }

        // Schedule 엔티티를 DTO로 변환하여 반환
        return schedules.map(ScheduleListResponseDto::from);
    }

    /**
     * 일정 수정
     * 일정 엔티티를 조회한 후, 일정 정보 수정
     *
     * @param scheduleId 수정할 일정의 ID
     * @param scheduleUpdateReqeustDto 수정할 제목과 내용을 담은 DTO
     * @return 수정된 일정 정보를 담은 응답 DTO
     */
    @Transactional
    public ScheduleUpdateResponseDto updateSchedule(final Long scheduleId, final ScheduleUpdateReqeustDto scheduleUpdateReqeustDto){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHEDULE_NOT_FOUND));

        // 엔티티 상태 변경 (JPA의 Dirty Checking)
        schedule.update(scheduleUpdateReqeustDto.todoTitle(), scheduleUpdateReqeustDto.todoContent(), scheduleUpdateReqeustDto.password());

        // 변경 감지 후 자동으로 updatedAt이 갱신됨 (Auditing)
        return ScheduleUpdateResponseDto.from(schedule);
    }

    /**
     * 일정 삭제
     *
     * @param scheduleId 삭제하려는 일정의 ID
     * @param scheduleDeleteRequestDto 사용자로부터 전달받은 삭제 요청 정보 (비밀번호 포함)
     */
    @Transactional
    public void deleteSchedule(final Long scheduleId, final ScheduleDeleteRequestDto scheduleDeleteRequestDto){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHEDULE_NOT_FOUND));

        // 비밀 번호 일치 여부 확인
        if (!scheduleDeleteRequestDto.password().equals(schedule.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        scheduleRepository.delete(schedule);
    }
}
