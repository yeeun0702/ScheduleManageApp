package com.example.schedulemanageapp.domain.comment.dto.response;

import com.example.schedulemanageapp.domain.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentUpdateResponseDto(
        Long commentId,
        String userName,
        String updatedContent,
        LocalDateTime updatedAt
) {
    public static CommentUpdateResponseDto from(Comment comment) {
        return new CommentUpdateResponseDto(
                comment.getCommentId(),
                comment.getUsers().getUserName(),
                comment.getCommentContent(),
                comment.getUpdatedAt()
        );
    }
}
