package com.example.schedulemanageapp.domain.comment.dto.response;

import com.example.schedulemanageapp.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentDetailResponseDto(
        Long commentId,
        String commentContent,
        String userName,
        Long scheduleId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentDetailResponseDto from(Comment comment) {
        return new CommentDetailResponseDto(
                comment.getCommentId(),
                comment.getCommentContent(),
                comment.getUsers().getUserName(),
                comment.getSchedule().getScheduleId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
