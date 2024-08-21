package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.request.UserUpdateInfoRequest;
import com.iamjunhyeok.review.dto.response.UserUpdateInfoResponse;
import com.iamjunhyeok.review.projection.UserSearchProjection;
import com.iamjunhyeok.review.projection.UserSummaryProjection;
import com.iamjunhyeok.review.projection.UserViewProjection;
import com.iamjunhyeok.review.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<UserUpdateInfoResponse> updateMyInfo(@RequestPart @Valid UserUpdateInfoRequest request,
                                                               @RequestPart MultipartFile file,
                                                               @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(UserUpdateInfoResponse.from(userService.updateUserInfo(user.getUserId(), request, file)));
    }

    /**
     * 사용자 정보 업데이트
     * @param id
     * @param request
     * @return
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateInfoResponse> updateUserInfo(@PathVariable Long id, @RequestBody @Valid UserUpdateInfoRequest request) {
        return ResponseEntity.ok(UserUpdateInfoResponse.from(userService.updateUserInfo(id, request)));
    }

    /**
     * 전체 사용자 조회
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserSearchProjection>> search() {
        return ResponseEntity.ok(userService.search());
    }

    /**
     * 사용자 상세정보 조회
     * @param id
     * @return
     */
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserViewProjection> view(@PathVariable Long id) {
        return ResponseEntity.ok(userService.view(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserViewProjection> summary(@AuthenticationPrincipal CustomOAuth2User principal) {
        return ResponseEntity.ok(userService.view(principal.getUserId()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/summary")
    public ResponseEntity<UserSummaryProjection> authenticatedUserSummary(@AuthenticationPrincipal CustomOAuth2User principal) {
        return ResponseEntity.ok(userService.summary(principal.getUserId()));
    }

//    @GetMapping("/me/campaigns")
//    public ResponseEntity<List<UserCampaignSearchProjection>> myCampaigns(@RequestParam String status,
//                                                                        @AuthenticationPrincipal CustomOAuth2User user) {
//        return ResponseEntity.ok(userService.fetchUserCampaigns(user.getUserId(), ApplicationStatus.valueOf(status.toUpperCase())));
//    }
//
//    /**
//     * 특정 사용자의 캠페인 조회
//     * @param id
//     * @param status
//     * @return
//     */
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/{id}/campaigns")
//    public ResponseEntity<List<UserCampaignSearchProjection>> userCampaigns(@PathVariable Long id,
//                                                                            @RequestParam String status) {
//        return ResponseEntity.ok(userService.fetchUserCampaigns(id, ApplicationStatus.valueOf(status.toUpperCase())));
//    }
//
//    @GetMapping("/{userId}/campaigns/{campaignId}/applications/{applicationId}")
//    public ResponseEntity<UserCampaignApplicationProjection> userCampaignApplication(@PathVariable Long userId,
//                                                                                     @PathVariable Long campaignId,
//                                                                                     @PathVariable Long applicationId) {
//        return ResponseEntity.ok(userService.fetchUserCampaignApplication(userId, campaignId, applicationId));
//    }
//
//    @GetMapping("/me/campaigns/{campaignId}/applications/{applicationId}")
//    public ResponseEntity<UserCampaignApplicationProjection> userCampaignApplication(@AuthenticationPrincipal CustomOAuth2User user,
//                                                                                     @PathVariable Long campaignId,
//                                                                                     @PathVariable Long applicationId) {
//        return ResponseEntity.ok(userService.fetchUserCampaignApplication(user.getUserId(), campaignId, applicationId));
//    }
//
//    @DeleteMapping("/me/campaigns/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable Long id, @AuthenticationPrincipal CustomOAuth2User user) {
//        userService.delete(id, user);
//    }
}