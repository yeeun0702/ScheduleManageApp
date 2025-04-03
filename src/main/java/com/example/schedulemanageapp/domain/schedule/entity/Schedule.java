package com.example.schedulemanageapp.domain.schedule.entity;

import com.example.schedulemanageapp.common.entity.BaseTimeEntity;
import com.example.schedulemanageapp.domain.comment.entity.Comment;
import com.example.schedulemanageapp.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(name="todo_title", nullable = false)
    private String todoTitle;

    @Column(name="todo_content", nullable = false)
    private String todoContent;

    @Column(name="password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    // 해당 일정에 달린 댓글 목록
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 생성자
    public Schedule(String todoTitle, String todoContent, String password, Users users) {
        this.todoTitle = todoTitle;
        this.todoContent = todoContent;
        this.password = password;
        this.users = users;
    }

    public void update(String todoTitle, String todoContent, String password) {
        this.todoTitle = todoTitle;
        this.todoContent = todoContent;
        this.password = password;
    }

}
