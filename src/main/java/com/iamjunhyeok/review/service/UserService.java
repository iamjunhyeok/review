package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.UserUpdateInfoRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.UserRepository;
import com.iamjunhyeok.review.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final S3Util s3Util;

    /**
     * 사용자의 기본정보를 업데이트하고, 프로필 이미지 변경이 존재하면 S3 에서 기존 오브젝트 삭제 후 새 오브젝트 추가
     * @param id
     * @param request
     * @param file
     * @return
     */
    @Transactional
    public User updateUserInfo(Long id, UserUpdateInfoRequest request, MultipartFile file) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());
        user.update(request);

        // 새로운 프로필 이미지가 업로드되었고, 이미지가 기존의 이미지와 다르면 기존의 이미지 S3 에서 삭제 처리
        String newProfileImageName = request.getProfileImageName();
        String originProfileImageName = user.getProfileImageName();
        if (!file.isEmpty() && Strings.isNotBlank(newProfileImageName)
                && !newProfileImageName.equals(originProfileImageName)) {
            try {
                s3Util.deleteObject(originProfileImageName);

                String newFilename = String.valueOf(UUID.randomUUID()).concat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
                s3Util.putObject(newFilename, file);

                user.updateProfileImageName(newFilename);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    /**
     * 사용자의 프로필 이미지를 제외한 기본정보만 변경
     * @param id
     * @param request
     * @return
     */
    @Transactional
    public User updateUserInfo(Long id, UserUpdateInfoRequest request) {
        return updateUserInfo(id, request, null);
    }
}
