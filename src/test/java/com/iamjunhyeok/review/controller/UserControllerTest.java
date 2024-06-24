package com.iamjunhyeok.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.UserUpdatePasswordRequest;
import com.iamjunhyeok.review.dto.UserJoinRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void 회원가입_유효한값_생성() throws Exception {
        String email = "jeonjhyeok@gmail.com";
        String nickname = "jeonjhyeok";
        String password = "1234";
        String confirmPassword = "1234";

        User user = User.createUser(email, nickname, password);
        when(userService.join(any(UserJoinRequest.class))).thenReturn(user);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(UserJoinRequest.of(email, password)));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password))
                .andDo(print());
    }

    @Test
    void 회원가입_중복된이메일_생성() throws Exception {
        String email = "jeonjhyeok@gmail.com";
        String nickname = "jeonjhyeok";
        String password = "1234";
        String confirmPassword = "1234";

        User user = User.createUser(email, nickname, password);
        when(userService.join(any(UserJoinRequest.class))).thenThrow(ErrorCode.DUPLICATE_EMAIL.build());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(UserJoinRequest.of(email, password)));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(ErrorCode.DUPLICATE_EMAIL.getMessage()))
                .andDo(print());
    }

    @Test
    void 비밀번호변경_유효한값_변경완료() throws Exception {
        String oldPassword = "1234";
        String newPassword = "5678";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/users/update-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(UserUpdatePasswordRequest.of(oldPassword, newPassword)));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 비밀번호_사용자를찾을수없음_404() throws Exception {
        String oldPassword = "1234";
        String newPassword = "5678";

        doThrow(ErrorCode.USER_NOT_FOUND.build()).when(userService).updatePassword(any(UserUpdatePasswordRequest.class));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/users/update-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(UserUpdatePasswordRequest.of(oldPassword, newPassword)));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()))
                .andDo(print());
    }

    @Test
    void 비밀번호변경_비밀번호불일치_400() throws Exception {
        String oldPassword = "1234";
        String newPassword = "5678";

        doThrow(ErrorCode.INCORRECT_PASSWORD.build()).when(userService).updatePassword(any(UserUpdatePasswordRequest.class));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/users/update-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(UserUpdatePasswordRequest.of(oldPassword, newPassword)));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.INCORRECT_PASSWORD.getMessage()))
                .andDo(print());
    }
}