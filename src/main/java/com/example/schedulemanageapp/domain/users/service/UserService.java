package com.example.schedulemanageapp.domain.users.service;

import com.example.schedulemanageapp.common.config.PasswordEncoder;
import com.example.schedulemanageapp.common.exception.base.CustomException;
import com.example.schedulemanageapp.common.exception.base.NotFoundException;
import com.example.schedulemanageapp.common.exception.code.enums.ErrorCode;
import com.example.schedulemanageapp.domain.users.dto.request.UserCreateRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserDeleteRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserLoginRequestDto;
import com.example.schedulemanageapp.domain.users.dto.request.UserUpdateRequestDto;
import com.example.schedulemanageapp.domain.users.dto.response.UserDetailResponseDto;
import com.example.schedulemanageapp.domain.users.dto.response.UserUpdateResponseDto;
import com.example.schedulemanageapp.domain.users.entity.Users;
import com.example.schedulemanageapp.domain.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 생성
     *
     * @param userCreateRequestDto 사용자 생성 요청 데이터를 담은 DTO
     */
    @Transactional
    public void createUser(final UserCreateRequestDto userCreateRequestDto, HttpServletRequest httpServletRequest) {

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userCreateRequestDto.password());

        // 이메일 중복 확인
        if (userRepository.findByEmail(userCreateRequestDto.email()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 빌더 패턴을 사용하여 사용자 객체 생성
        Users user = Users.builder()
                .userName(userCreateRequestDto.userName())
                .email(userCreateRequestDto.email())
                .password(encodedPassword)
                .build();  // 빌드 호출

        userRepository.save(user);

        // 회원가입 후 세션에 사용자 정보 저장
        HttpSession httpSession = httpServletRequest.getSession(true);  // 새로운 세션 생성
        httpSession.setAttribute("user", user);  // 세션에 사용자 정보 저장
    }

    @Transactional
    public boolean login(final UserLoginRequestDto userLoginRequestDto, HttpServletRequest httpServletRequest) {

        // 이메일과 사용자 이름을 기준으로 사용자 조회
        Users user = userRepository.findByEmail(userLoginRequestDto.email())
                .filter(u -> u.getUserName().equals(userLoginRequestDto.userName()))  // 사용자 이름의 일치 여부도 확인
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));

        // 비밀번호 확인
        if (!passwordEncoder.matches(userLoginRequestDto.password(), user.getPassword())) {
            // 비밀번호가 틀린 경우 예외 처리
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 세션을 통해 사용자 정보 저장
        HttpSession httpSession = httpServletRequest.getSession(true);  // 새로운 세션을 생성
        httpSession.setAttribute("user", user);  // 세션에 사용자 정보 저장

        return true; // 로그인 성공 시 true 반환
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
