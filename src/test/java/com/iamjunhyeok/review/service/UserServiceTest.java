package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.UserChangePasswordRequest;
import com.iamjunhyeok.review.dto.UserJoinRequest;
import com.iamjunhyeok.review.exception.ApplicationException;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void 회원가입_중복된이메일이존재하지않음() {
        UserJoinRequest request = new UserJoinRequest();
        request.setEmail("abc@gmail.com");
        request.setPassword("1234");

        User user = new User();
        user.setId(1L);
        user.setEmail("abc@gmail.com");
        user.setPassword("1234");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        User saved = userService.join(request);

        assertEquals(request.getEmail(), saved.getEmail());
        assertEquals(request.getPassword(), saved.getPassword());
    }

    @Test
    void 회원가입_중복된이메일이존재() {
        UserJoinRequest request = new UserJoinRequest(  );
        request.setEmail("abc@gmail.com");
        request.setPassword("1234");

        User user = new User();
        user.setId(1L);
        user.setEmail("abc@gmail.com");
        user.setPassword("1234");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        ApplicationException exception = assertThrows(ApplicationException.class, () -> userService.join(request));

        assertEquals(ErrorCode.DUPLICATE_EMAIL.getHttpStatus(), exception.getHttpStatus());
        assertEquals(ErrorCode.DUPLICATE_EMAIL.getMessage(), exception.getMessage());
    }

    @Test
    void 비밀번호변경_사용자를찾을수없음() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserChangePasswordRequest request = new UserChangePasswordRequest();
        request.setNewPassword("");
        request.setOldPassword("");

        ApplicationException exception = assertThrows(ApplicationException.class, () -> userService.changePassword(request));

        assertEquals(ErrorCode.USER_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
    }

    @Test
    void 비밀번호변경_사용자를찾을수있음() {
        User user = new User();
        user.setId(1L);
        user.setEmail("abc@gmail.com");
        user.setPassword("1234");

        User spyUser = spy(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(spyUser));

        UserChangePasswordRequest request = new UserChangePasswordRequest();
        request.setOldPassword("");
        request.setNewPassword("5678");

        userService.changePassword(request);

        assertEquals(request.getNewPassword(), spyUser.getPassword());
        verify(spyUser, times(1)).changePassword(request.getNewPassword());
    }
}