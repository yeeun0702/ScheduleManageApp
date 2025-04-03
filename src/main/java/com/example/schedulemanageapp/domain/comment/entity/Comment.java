package com.example.schedulemanageapp.domain.comment.entity;

import com.example.schedulemanageapp.common.entity.BaseTimeEntity;
import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import com.example.schedulemanageapp.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    // 댓글 수정 메서드
    public void update(String newContent) {
        this.commentContent = newContent;
    }
}
