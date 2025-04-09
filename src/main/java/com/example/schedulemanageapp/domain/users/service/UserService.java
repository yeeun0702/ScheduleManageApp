package com.example.schedulemanageapp.domain.users.service;

import com.example.schedulemanageapp.common.config.PasswordEncoder;
import com.example.schedulemanageapp.common.exception.base.CustomException;
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

        userRepository.save(user); // 로그인 한 건 아니므로 세션 저장은 X
    }

    @Transactional
    public void login(final UserLoginRequestDto dto, HttpServletRequest request) {

        // 사용자 조회
        Users user = userRepository.findByEmail(dto.email())
                .filter(u -> u.getUserName().equals(dto.userName()))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_REGISTERED));

        // 비밀번호 확인
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
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
