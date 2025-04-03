package com.example.schedulemanageapp.domain.comment.service;

import com.example.schedulemanageapp.common.exception.base.CustomException;
import com.example.schedulemanageapp.common.exception.base.NotFoundException;
import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;
import com.example.schedulemanageapp.domain.comment.dto.request.CommentCreateRequestDto;
import com.example.schedulemanageapp.domain.comment.dto.request.CommentUpdateRequestDto;
import com.example.schedulemanageapp.domain.comment.dto.response.CommentDetailResponseDto;
import com.example.schedulemanageapp.domain.comment.dto.response.CommentListResponseDto;
import com.example.schedulemanageapp.domain.comment.dto.response.CommentUpdateResponseDto;
import com.example.schedulemanageapp.domain.comment.entity.Comment;
import com.example.schedulemanageapp.domain.comment.repository.CommentRepository;
import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import com.example.schedulemanageapp.domain.schedule.repository.ScheduleRepository;
import com.example.schedulemanageapp.domain.users.entity.Users;
import com.example.schedulemanageapp.domain.users.service.UserServiceHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserServiceHelper userServiceHelper;

    /**
     * 댓글 생성
     */
    @Transactional
    public void createComment(@Valid final CommentCreateRequestDto commentCreateRequestDto) {
        Users user = userServiceHelper.findUserOrThrow(commentCreateRequestDto.userId());
        Schedule schedule = scheduleRepository.findById(commentCreateRequestDto.scheduleId())
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        Comment comment = Comment.builder()
                .users(user)
                .schedule(schedule)
                .commentContent(commentCreateRequestDto.commentContent())
                .build();

        commentRepository.save(comment);
    }
    /**
     * 댓글 단건 조회
     */
    @Transactional
    public CommentDetailResponseDto getComment(final Long commentId) {
        return commentRepository.findById(commentId)
                .map(CommentDetailResponseDto::from)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }

    /**
     * 특정 일정의 전체 댓글 조회
     */
    @Transactional
    public List<CommentListResponseDto> getCommentsBySchedule(final Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHEDULE_NOT_FOUND));

        return commentRepository.findAllBySchedule(schedule).stream()
                .map(CommentListResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public CommentUpdateResponseDto updateComment(final Long commentId, final CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        comment.update(commentUpdateRequestDto.commentContent());

        return CommentUpdateResponseDto.from(comment);
    }
}
