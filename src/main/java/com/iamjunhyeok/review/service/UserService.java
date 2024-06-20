package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.VocaType;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.domain.Voca;
import com.iamjunhyeok.review.dto.UserJoinRequest;
import com.iamjunhyeok.review.dto.UserUpdateInfoRequest;
import com.iamjunhyeok.review.dto.UserUpdatePasswordRequest;
import com.iamjunhyeok.review.exception.ApplicationException;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.UserRepository;
import com.iamjunhyeok.review.repository.VocaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final VocaRepository vocaRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User join(UserJoinRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw ErrorCode.DUPLICATE_EMAIL.build();
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw ErrorCode.DUPLICATE_NICKNAME.build();
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw ErrorCode.PASSWORDS_DO_NOT_MATCH.build();
        }
        return userRepository.save(User.createUser(request.getEmail(), request.getNickname(), passwordEncoder.encode(request.getPassword())));
    }

    @Transactional
    public void updatePassword(UserUpdatePasswordRequest request) {
        // 로그인이 개발되면 ID 값 매개변수 변경할 것!!
        userRepository.findById(1L)
                .ifPresentOrElse(user -> {
                    if (user.getPassword().equals(request.getCurrentPassword())) {
                        user.updatePassword(request.getNewPassword(), request.getConfirmNewPassword());
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
                .ifPresentOrElse(user -> user.updateInfo(request.getNickname()), () -> {
                    throw ErrorCode.USER_NOT_FOUND.build();
                });
    }

    public String generateNickname() {
        Map<VocaType, List<Voca>> groupedByType = vocaRepository.findAll()
                .stream().collect(groupingBy(Voca::getType));
        List<Voca> adjectives = groupedByType.get(VocaType.ADJECTIVE);
        List<Voca> nouns = groupedByType.get(VocaType.NOUN);
        StringBuilder nickname;
        int attempts = 0;
        do {
            if (attempts > 100) {
                throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "에러가 발생했습니다.");
            }
            nickname = new StringBuilder();
            nickname.append(adjectives.get(new Random().nextInt(adjectives.size())).getValue());
            nickname.append(nouns.get(new Random().nextInt(adjectives.size())).getValue());
            attempts++;
        } while (userRepository.existsByNickname(nickname.toString()));
        return nickname.toString();
    }
}
