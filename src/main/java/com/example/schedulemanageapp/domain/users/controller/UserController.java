package com.example.schedulemanageapp.domain.users.controller;

import com.example.schedulemanageapp.common.exception.code.enums.SuccessCode;
import com.example.schedulemanageapp.common.response.ApiResponseDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserCreateRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserDeleteRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserUpdateRequestDto;
import com.example.schedulemanageapp.domain.users.dto.response.UserDetailResponseDto;
import com.example.schedulemanageapp.domain.users.dto.response.UserUpdateResponseDto;
import com.example.schedulemanageapp.domain.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<Void>> createUser(@RequestBody @Valid final UserCreateRequestDto userCreateRequestDto) {
        userService.createUser(userCreateRequestDto);
        return ResponseEntity.status(201)
                .body(ApiResponseDto.success(SuccessCode.USER_CREATE_SUCCESS, "/api/users"));
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
