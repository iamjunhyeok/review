package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.dto.UserUpdateInfoRequest;
import com.iamjunhyeok.review.dto.UserUpdatePasswordRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updatePassword(UserUpdatePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw ErrorCode.PASSWORDS_DO_NOT_MATCH.build();
        }
        // 로그인이 개발되면 ID 값 매개변수 변경할 것!!
        userRepository.findById(1L)
                .ifPresentOrElse(user -> {
                    if (user.getPassword().equals(request.getCurrentPassword())) {
                        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
                    } else {
                        throw ErrorCode.INCORRECT_PASSWORD.build();
                    }
                }, () -> {
                    throw ErrorCode.USER_NOT_FOUND.build();
                });
    }

    @Transactional
    public void updateMyInfo(UserUpdateInfoRequest request) {
        // 로그인이 개발되면 ID 값 매개변수 변경할 것!!
        updateUserInfo(1L, request);
    }

    @Transactional
    public void updateUserInfo(Long id, UserUpdateInfoRequest request) {
        userRepository.findById(id)
                .ifPresentOrElse(user -> user.updateInfo(request.getNickname(), request.getAddress(), request.getRest(), request.getPostalCode()), () -> {
                    throw ErrorCode.USER_NOT_FOUND.build();
                });
    }
}
