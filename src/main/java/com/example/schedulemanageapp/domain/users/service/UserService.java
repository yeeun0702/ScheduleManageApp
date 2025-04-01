package com.example.schedulemanageapp.domain.users.service;

import com.example.schedulemanageapp.common.exception.base.CustomException;
import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;
import com.example.schedulemanageapp.domain.users.dto.request.UserCreateRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserDeleteRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserUpdateRequestDto;
import com.example.schedulemanageapp.domain.users.dto.response.UserDetailResponseDto;
import com.example.schedulemanageapp.domain.users.dto.response.UserUpdateResponseDto;
import com.example.schedulemanageapp.domain.users.entity.Users;
import com.example.schedulemanageapp.domain.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;

    @Transactional
    public void createUser(final UserCreateRequestDto userCreateRequestDto){
        Users user = new Users(
                userCreateRequestDto.userName(),
                userCreateRequestDto.email(),
                userCreateRequestDto.password()
        );
        userRepository.save(user);
    }

    @Transactional
    public UserDetailResponseDto getUser(final Long userId){
        Users user = userServiceHelper.findUserOrThrow(userId);
        return UserDetailResponseDto.from(user);
    }

    @Transactional
    public UserUpdateResponseDto updateUser(final Long userId, final UserUpdateRequestDto userUpdateRequestDto){

        Users user = userServiceHelper.findUserOrThrow(userId);
        user.update(userUpdateRequestDto.userName(), userUpdateRequestDto.email(), userUpdateRequestDto.password());

        return UserUpdateResponseDto.from(user);
    }

    @Transactional
    public void deleteUser(final Long userId, final UserDeleteRequestDto userDeleteRequestDto){

        Users user = userServiceHelper.findUserOrThrow(userId);

        // 비밀 번호 일치 여부 확인
        if (!userDeleteRequestDto.password().equals(user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        userRepository.deleteById(userId);
    }

}
