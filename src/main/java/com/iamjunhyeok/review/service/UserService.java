package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.UserChangePasswordRequest;
import com.iamjunhyeok.review.dto.UserJoinRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User join(UserJoinRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw ErrorCode.DUPLICATE_EMAIL.build();
                });
        return userRepository.save(User.createUser(request.getEmail(), request.getPassword()));
    }

    @Transactional
    public void changePassword(UserChangePasswordRequest request) {
        userRepository.findById(1L)
                .ifPresentOrElse(user -> user.changePassword(request.getNewPassword()), () -> {
                    throw ErrorCode.USER_NOT_FOUND.build();
                });
    }
}
