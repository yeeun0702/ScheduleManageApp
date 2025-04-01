package com.example.schedulemanageapp.domain.schedule.service;

import com.example.schedulemanageapp.common.exception.base.CustomException;
import com.example.schedulemanageapp.common.exception.base.NotFoundException;
import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;
import com.example.schedulemanageapp.domain.schedule.dto.request.ScheduleCreateRequestDto;
import com.example.schedulemanageapp.domain.schedule.dto.response.ScheduleDetailResponseDto;
import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import com.example.schedulemanageapp.domain.schedule.repository.ScheduleRepository;
import com.example.schedulemanageapp.domain.users.entity.Users;
import com.example.schedulemanageapp.domain.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
