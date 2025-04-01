package com.example.schedulemanageapp.domain.schedule.repository;

import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /* - userId가 null이 아니면 해당 유저의 일정만 필터링
       - updatedDate가 null이 아니면 수정일 기준 이후의 일정만 필터링
     */
    @Query("""
        SELECT s FROM Schedule s
        WHERE (:userId IS NULL OR s.users.id = :userId)
          AND (:updatedDate IS NULL OR s.updatedAt >= :updatedDate)
        ORDER BY s.updatedAt DESC
    """)
    List<Schedule> findSchedulesByConditions(
        Long userId,
        LocalDateTime updatedDate
    );
}
