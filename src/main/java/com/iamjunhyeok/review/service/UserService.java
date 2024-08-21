package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.ApplicationStatus;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.request.UserUpdateInfoRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.UserCampaignApplicationProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import com.iamjunhyeok.review.projection.UserSearchProjection;
import com.iamjunhyeok.review.projection.UserSummaryProjection;
import com.iamjunhyeok.review.projection.UserViewProjection;
import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import com.iamjunhyeok.review.util.S3Util;
import io.jsonwebtoken.lang.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final S3Util s3Util;
    private final ApplicationRepository applicationRepository;

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
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build())
                .update(request);

        // 새로운 프로필 이미지가 업로드되었고, 이미지가 기존의 이미지와 다르면 기존의 이미지 S3 에서 삭제 처리
        String newProfileImageName = request.getProfileImageName();
        String originProfileImageName = user.getProfileImageName();
        if (!Objects.isEmpty(file) && Strings.isNotBlank(newProfileImageName)
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

    public List<UserSearchProjection> search() {
        return userRepository.search();
    }

    public UserViewProjection view(Long id) {
        return userRepository.fetchById(id)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());
    }

    public List<UserCampaignSearchProjection> fetchUserCampaigns(Long id, ApplicationStatus applicationStatus) {
        return userRepository.fetchAllByUserIdAndApplicationStatus(id, applicationStatus);
    }

    public UserCampaignApplicationProjection fetchUserCampaignApplication(Long userId, Long campaignId, Long applicationId) {
        return userRepository.fetchUserCampaignApplication(userId, campaignId, applicationId);
    }

    @Transactional
    public void delete(Long id, CustomOAuth2User user) {
        applicationRepository.findById(id)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build())
                .delete();
    }

    public UserSummaryProjection summary(Long userId) {
        return userRepository.fetchUserSummary(userId);
    }
}
