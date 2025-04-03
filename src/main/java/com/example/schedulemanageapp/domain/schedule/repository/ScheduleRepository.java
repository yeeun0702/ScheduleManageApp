package com.example.schedulemanageapp.domain.schedule.repository;

import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 조건 기반 페이징 쿼리
    Page<Schedule> findByUsers_UserId(Long userId, Pageable pageable);

    Page<Schedule> findByUpdatedAtAfter(LocalDateTime updatedDate, Pageable pageable);

    Page<Schedule> findByUsers_UserIdAndUpdatedAtAfter(Long userId, LocalDateTime updatedDate, Pageable pageable);

    Page<Schedule> findAll(Pageable pageable); // 기본 findAll도 사용 가능

}
