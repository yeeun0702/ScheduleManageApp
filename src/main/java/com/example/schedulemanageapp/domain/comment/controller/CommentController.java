package com.example.schedulemanageapp.domain.comment.controller;

import com.example.schedulemanageapp.common.exception.code.enums.SuccessCode;
import com.example.schedulemanageapp.common.response.ApiResponseDto;
import com.example.schedulemanageapp.domain.comment.dto.request.CommentCreateRequestDto;
import com.example.schedulemanageapp.domain.comment.dto.request.CommentUpdateRequestDto;
import com.example.schedulemanageapp.domain.comment.dto.response.CommentDetailResponseDto;
import com.example.schedulemanageapp.domain.comment.dto.response.CommentListResponseDto;
import com.example.schedulemanageapp.domain.comment.dto.response.CommentUpdateResponseDto;
import com.example.schedulemanageapp.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<Void>> createComment(@RequestBody @Valid final CommentCreateRequestDto commentCreateRequestDto) {
        commentService.createComment(commentCreateRequestDto);
        return ResponseEntity.status(201)
                .body(ApiResponseDto.success(SuccessCode.COMMENT_CREATE_SUCCESS, "/api/comments"));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto<CommentDetailResponseDto>> getComment(@PathVariable final Long commentId) {
        return ResponseEntity.ok(
                ApiResponseDto.success(SuccessCode.COMMENT_READ_SUCCESS, commentService.getComment(commentId), "/api/comments/")
        );
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ApiResponseDto<List<CommentListResponseDto>>> getCommentsBySchedule(@PathVariable final Long scheduleId) {
        return ResponseEntity.ok(
                ApiResponseDto.success(SuccessCode.COMMENT_LIST_SUCCESS, commentService.getCommentsBySchedule(scheduleId), "/api/comments/schedule/" + scheduleId)
        );
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto<CommentUpdateResponseDto>> updateComment(
            @PathVariable final Long commentId,
            @RequestBody @Valid final CommentUpdateRequestDto commentUpdateRequestDto) {
        return ResponseEntity.ok(
                ApiResponseDto.success(SuccessCode.COMMENT_UPDATE_SUCCESS, commentService.updateComment(commentId, commentUpdateRequestDto), "/api/comments")
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable final Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

}
