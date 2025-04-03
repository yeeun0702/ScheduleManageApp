package com.example.schedulemanageapp.domain.users.controller;

import com.example.schedulemanageapp.common.exception.base.CustomException;
import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;
import com.example.schedulemanageapp.common.exception.code.enums.SuccessCode;
import com.example.schedulemanageapp.common.response.ApiResponseDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserCreateRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserDeleteRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserLoginRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserUpdateRequestDto;
import com.example.schedulemanageapp.domain.users.dto.response.UserDetailResponseDto;
import com.example.schedulemanageapp.domain.users.dto.response.UserUpdateResponseDto;
import com.example.schedulemanageapp.domain.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<Void>> createUser(
            @RequestBody @Valid final UserCreateRequestDto userCreateRequestDto,
            HttpServletRequest httpServletRequest) {
        userService.createUser(userCreateRequestDto, httpServletRequest);
        return ResponseEntity.status(201)
                .body(ApiResponseDto.success(SuccessCode.USER_SIGNUP_SUCCESS, "/api/users"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<Void>> login(
            @RequestBody @Valid final UserLoginRequestDto userLoginRequestDto,
            HttpServletRequest httpServletRequest) {

        try {
            boolean loginSuccess = userService.login(userLoginRequestDto, httpServletRequest);

            if (loginSuccess) {
                return ResponseEntity.ok(ApiResponseDto.success(SuccessCode.USER_LOGIN_SUCCESS, "/api/users/login"));
            }
        }
        catch (CustomException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDto.fail(ErrorCode.UNAUTHORIZED, "api/users/login"));
        }

        // 기본적으로 실패 시 반환 (기타 예외 처리)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.fail(ErrorCode.INTERNAL_SERVER_ERROR, "서버 오류"));
    }


    @GetMapping("{userId}")
    public ResponseEntity<ApiResponseDto<UserDetailResponseDto>> getUser(@PathVariable final Long userId){
        return ResponseEntity.ok(ApiResponseDto.success(SuccessCode.USER_READ_SUCCESS, userService.getUser(userId), "/api/users/"));
    }

    @PutMapping("{userId}")
    public ResponseEntity<ApiResponseDto<UserUpdateResponseDto>> updateUser(
            @PathVariable final Long userId,
            @RequestBody @Valid final UserUpdateRequestDto userUpdateRequestDto){
        return ResponseEntity.ok(ApiResponseDto.success(SuccessCode.USER_UPDATE_SUCCESS, userService.updateUser(userId, userUpdateRequestDto), "api/users"));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteUser(
            @PathVariable final Long userId,
            @RequestBody @Valid final UserDeleteRequestDto userDeleteRequestDto){
        userService.deleteUser(userId, userDeleteRequestDto);
        return ResponseEntity.noContent().build();
    }

}
