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

    /**
     * 유저 생성
     *
     * @param userCreateRequestDto 사용자 생성 요청 데이터를 담은 DTO
     */
    @Transactional
    public void createUser(final UserCreateRequestDto userCreateRequestDto){
        Users user = new Users(
                userCreateRequestDto.userName(),
                userCreateRequestDto.email(),
                userCreateRequestDto.password()
        );
        userRepository.save(user);
    }


    /**
     * 유저 조회
     *
     * @param userId 조회할 사용자 ID
     * @return UserDetailResponseDto 조회된 사용자 정보
     */
    @Transactional
    public UserDetailResponseDto getUser(final Long userId){
        Users user = userServiceHelper.findUserOrThrow(userId);
        return UserDetailResponseDto.from(user);
    }


    /**
     * 유저 수정
     *
     * @param userId 수정할 사용자 ID
     * @param userUpdateRequestDto 수정할 사용자 정보가 담긴 DTO
     * @return UserUpdateResponseDto 수정된 사용자 정보
     */
    @Transactional
    public UserUpdateResponseDto updateUser(final Long userId, final UserUpdateRequestDto userUpdateRequestDto){

        Users user = userServiceHelper.findUserOrThrow(userId);
        user.update(userUpdateRequestDto.userName(), userUpdateRequestDto.email(), userUpdateRequestDto.password());

        return UserUpdateResponseDto.from(user);
    }


    /**
     * 유저 삭제
     * 사용자가 삭제 요청을 할 때, 비밀번호가 맞는지 확인한 후 삭제 처리.
     *
     * @param userId 삭제할 사용자 ID
     * @param userDeleteRequestDto 사용자 삭제 요청 데이터 (비밀번호 포함)
     */
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
