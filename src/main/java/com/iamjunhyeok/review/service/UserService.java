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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final ApplicationRepository applicationRepository;

    private final S3Util s3Util;

    private static final String PROFILE_BUCKET_NAME = "olim-profile";

    @Transactional
    public User updateUserInfo(UserUpdateInfoRequest request, MultipartFile file, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build())
                .update(request);

        if (Objects.nonNull(file) && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = String.valueOf(user.getId()).concat(extension);
            s3Util.putObject(PROFILE_BUCKET_NAME, file, fileName);
        }
        return user;
    }

    @Transactional
    public User updateUserInfo(UserUpdateInfoRequest request, Long userId) throws IOException {
        return updateUserInfo(request, null, userId);
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
