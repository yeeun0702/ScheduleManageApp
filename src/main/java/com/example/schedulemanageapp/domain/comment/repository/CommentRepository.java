package com.example.schedulemanageapp.domain.comment.repository;

import com.example.schedulemanageapp.domain.comment.entity.Comment;
import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 일정에 속한 모든 댓글 조회
    List<Comment> findAllBySchedule(Schedule schedule);
}
