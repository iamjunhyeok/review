package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.UserChangePasswordRequest;
import com.iamjunhyeok.review.dto.UserJoinRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User join(UserJoinRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw ErrorCode.DUPLICATE_EMAIL.build();
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw ErrorCode.DUPLICATE_NICKNAME.build();
        }
        return userRepository.save(User.createUser(request.getEmail(), request.getNickname(), request.getPassword(), request.getConfirmPassword()));
    }

    @Transactional
    public void changePassword(UserChangePasswordRequest request) {
        // 로그인이 개발되면 ID 값 매개변수 변경할 것!!
        userRepository.findById(1L)
                .ifPresentOrElse(user -> {
                    if (user.getPassword().equals(request.getCurrentPassword())) {
                        user.changePassword(request.getNewPassword(), request.getConfirmNewPassword());
                    } else {
                        throw ErrorCode.INCORRECT_PASSWORD.build();
                    }
                }, () -> {
                    throw ErrorCode.USER_NOT_FOUND.build();
                });
    }
}
