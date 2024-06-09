package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.UserDto;
import com.iamjunhyeok.review.dto.UserJoinRequest;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto join(UserJoinRequest request) {
        return UserDto.from(userRepository.save(User.createUser(request.getEmail(), request.getPassword())));
    }
}
