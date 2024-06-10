package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.UserJoinRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User join(UserJoinRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> { throw ErrorCode.DUPLICATE_EMAIL.build(); });
        return userRepository.save(User.createUser(request.getEmail(), request.getPassword()));
    }
}
