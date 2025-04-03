package com.example.schedulemanageapp.domain.users.entity;

import com.example.schedulemanageapp.common.entity.BaseTimeEntity;
import com.example.schedulemanageapp.domain.comment.entity.Comment;
import com.example.schedulemanageapp.domain.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name="user_name", nullable = false)
    private String userName;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    // 작성한 일정들
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    // 작성한 댓글들
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Users(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void update(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

}
