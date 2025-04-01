package com.example.schedulemanageapp.domain.schedule.entity;

import com.example.schedulemanageapp.common.entity.BaseTimeEntity;
import com.example.schedulemanageapp.domain.users.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    // 생성자
    public Schedule(String todoTitle, String todoContent, Users users) {
        this.todoTitle = todoTitle;
        this.todoContent = todoContent;
        this.users = users;
    }

}
