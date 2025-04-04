-- Users 테이블 (작성자 정보)
CREATE TABLE Users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 작성자 ID (기본키)
                       name VARCHAR(255) NOT NULL,                 -- 이름
                       password VARCHAR(255) NOT NULL,             -- 비밀번호
                       email VARCHAR(255) NOT NULL,                -- 이메일
                       created_at DATETIME NOT NULL DEFAULT NOW(), -- 작성일
                       updated_at DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW() -- 수정일
);

-- Schedule 테이블 (일정 정보)
CREATE TABLE Schedule (
                          schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 일정 ID (기본키)
                          user_id BIGINT NOT NULL,                        -- 작성자 ID (Users 테이블 참조)
                          todo_title VARCHAR(255) NOT NULL,               -- 할 일 제목
                          todo_content VARCHAR(1000) NOT NULL,            -- 할 일 내용
                          created_at DATETIME NOT NULL DEFAULT NOW(),     -- 작성일
                          updated_at DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(), -- 수정일
                          FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Comment 테이블 (댓글 정보)
CREATE TABLE Comment (
                         comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,   -- 댓글 ID (기본키)
                         schedule_id BIGINT NOT NULL,                    -- 일정 ID (Schedule 테이블 참조)
                         user_id BIGINT NOT NULL,                        -- 작성자 ID (Users 테이블 참조)
                         comment_content VARCHAR(1000) NOT NULL,         -- 댓글 내용
                         created_at DATETIME NOT NULL DEFAULT NOW(),     -- 작성일
                         updated_at DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(), -- 수정일
                         FOREIGN KEY (schedule_id) REFERENCES Schedule(schedule_id) ON DELETE CASCADE,
                         FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);
