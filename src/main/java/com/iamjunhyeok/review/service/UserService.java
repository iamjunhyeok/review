package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.UserUpdateInfoRequest;
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
    public User updateMyInfo(UserUpdateInfoRequest request) {
        // 로그인이 개발되면 ID 값 매개변수 변경할 것!!
        return updateUserInfo(1L, request);
    }

    @Transactional
    public User updateUserInfo(Long id, UserUpdateInfoRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());
        return user.update(request);
    }
}
