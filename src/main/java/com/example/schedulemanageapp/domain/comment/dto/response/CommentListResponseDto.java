package com.example.schedulemanageapp.domain.comment.dto.response;

import com.example.schedulemanageapp.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentListResponseDto(
        Long commentId,
        String commentContent,
        String userName,
        LocalDateTime createdAt
) {
    public static CommentListResponseDto from(Comment comment) {
        return new CommentListResponseDto(
                comment.getCommentId(),
                comment.getCommentContent(),
                comment.getUsers().getUserName(),
                comment.getCreatedAt()
        );
    }
}
