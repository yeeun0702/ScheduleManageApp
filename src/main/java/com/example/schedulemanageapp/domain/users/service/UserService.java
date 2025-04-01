package com.example.schedulemanageapp.domain.users.service;

import com.example.schedulemanageapp.common.exception.base.NotFoundException;
import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;
import com.example.schedulemanageapp.domain.users.dto.request.UserCreateRequestDto;
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
        return userRepository.findById(userId)
                .map(UserDetailResponseDto::from)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public UserUpdateResponseDto updateUser(final Long userId, final UserUpdateRequestDto userUpdateRequestDto){

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        user.update(userUpdateRequestDto.userName(), userUpdateRequestDto.email(), userUpdateRequestDto.password());

        return UserUpdateResponseDto.from(user);
    }

}
