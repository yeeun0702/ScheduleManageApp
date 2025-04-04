## 🗓️ Schedule Manage App

> Spring Boot 기반 일정 관리 서비스
> 
> 
> 사용자 인증, 일정 관리, 댓글 기능을 지원합니다.
> 


### ⚙️ 기술 스택

| 분야 | 사용 기술 |
| --- | --- |
| Backend | Spring Boot, Spring Data JPA |
| DB | MySQL |
| ORM | Hibernate (JPA) |
| Build Tool | Gradle |
| Dev Tool | Lombok, Spring Boot DevTools |



### 🗂️ 디렉토리 구조

```
ScheduleManageApp:.
│  ScheduleManageAppApplication.java
│
├─common
│  ├─advice
│  │      GlobalExceptionHandler.java
│  │      ResponseStatusSetterAdvice.java
│  │
│  ├─config
│  │      FilterConfig.java
│  │      PasswordEncoder.java
│  │
│  ├─entity
│  │      BaseTimeEntity.java
│  │
│  ├─exception
│  │  ├─base
│  │  │      BadRequestException.java
│  │  │      CustomException.java
│  │  │      NotFoundException.java
│  │  │
│  │  └─code
│  │      └─enums
│  │              ErrorCode.java
│  │              SuccessCode.java
│  │
│  ├─filter
│  │      LoginFilter.java
│  │
│  └─response
│          ApiResponseDto.java
│
└─domain
    ├─comment
    │  ├─controller
    │  │      CommentController.java
    │  │
    │  ├─dto
    │  │  ├─request
    │  │  │      CommentCreateRequestDto.java
    │  │  │      CommentUpdateRequestDto.java
    │  │  │
    │  │  └─response
    │  │          CommentDetailResponseDto.java
    │  │          CommentListResponseDto.java
    │  │          CommentUpdateResponseDto.java
    │  │
    │  ├─entity
    │  │      Comment.java
    │  │
    │  ├─repository
    │  │      CommentRepository.java
    │  │
    │  └─service
    │          CommentService.java
    │          CommentServiceHelper.java
    │
    ├─schedule
    │  ├─controller
    │  │      ScheduleController.java
    │  │
    │  ├─dto
    │  │  ├─request
    │  │  │      ScheduleCreateRequestDto.java
    │  │  │      ScheduleDeleteRequestDto.java
    │  │  │      ScheduleUpdateReqeustDto.java
    │  │  │
    │  │  └─response
    │  │          ScheduleDetailResponseDto.java
    │  │          ScheduleListResponseDto.java
    │  │          ScheduleUpdateResponseDto.java
    │  │
    │  ├─entity
    │  │      Schedule.java
    │  │
    │  ├─repository
    │  │      ScheduleRepository.java
    │  │
    │  └─service
    │          ScheduleService.java
    │
    └─users
        ├─controller
        │      UserController.java
        │
        ├─dto
        │  ├─request
        │  │      UserCreateRequestDto.java
        │  │      UserDeleteRequestDto.java
        │  │      UserLoginRequestDto.java
        │  │      UserUpdateRequestDto.java
        │  │
        │  └─response
        │          UserDetailResponseDto.java
        │          UserUpdateResponseDto.java
        │
        ├─entity
        │      Users.java
        │
        ├─repository
        │      UserRepository.java
        │
        └─service
                UserService.java
                UserServiceHelper.java
```



### 🧪 API 명세서
[API 명세서](https://www.notion.so/API-1c761a650f0780cea5e0fa85b26f0983?pvs=21)

### **🗃 ERD 설계**

![image](https://github.com/user-attachments/assets/6a994521-ad9b-4da0-84f6-a57ba5128b66)


