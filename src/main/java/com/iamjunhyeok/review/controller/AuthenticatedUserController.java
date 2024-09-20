package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.request.PointWithdrawalRequest;
import com.iamjunhyeok.review.dto.request.UserUpdateInfoRequest;
import com.iamjunhyeok.review.projection.ApplicationSearchProjection;
import com.iamjunhyeok.review.projection.InquiryProjection;
import com.iamjunhyeok.review.projection.PenaltyProjection;
import com.iamjunhyeok.review.projection.PointProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import com.iamjunhyeok.review.projection.UserSummaryProjection;
import com.iamjunhyeok.review.projection.UserView;
import com.iamjunhyeok.review.projection.WithdrawalProjection;
import com.iamjunhyeok.review.service.ApplicationService;
import com.iamjunhyeok.review.service.CampaignService;
import com.iamjunhyeok.review.service.InquiryService;
import com.iamjunhyeok.review.service.PenaltyService;
import com.iamjunhyeok.review.service.PointService;
import com.iamjunhyeok.review.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class AuthenticatedUserController {

    private final UserService userService;

    private final CampaignService campaignService;

    private final ApplicationService applicationService;

    private final PenaltyService penaltyService;

    private final PointService pointService;

    private final InquiryService inquiryService;

    @GetMapping
    public ResponseEntity<UserView> summary(@AuthenticationPrincipal CustomOAuth2User principal) {
        return ResponseEntity.ok(userService.view(principal.getUserId()));
    }

    @GetMapping("/summary")
    public ResponseEntity<UserSummaryProjection> authenticatedUserSummary(@AuthenticationPrincipal CustomOAuth2User principal) {
        return ResponseEntity.ok(userService.summary(principal.getUserId()));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateMyInfo(@RequestPart @Valid UserUpdateInfoRequest request,
                             @RequestPart MultipartFile file,
                             @AuthenticationPrincipal CustomOAuth2User principal) throws IOException {
        userService.updateUserInfo(request, file, principal.getUserId());
    }

    @GetMapping("/campaigns")
    public ResponseEntity<List<UserCampaignSearchProjection>> fetchAuthenticatedUserCampaigns(@RequestParam String status) {
        return ResponseEntity.ok(campaignService.fetchAuthenticatedUserCampaigns(status));
    }

    @GetMapping("/campaigns/applications")
    public ResponseEntity<List<ApplicationSearchProjection>> fetchAllApplications(@RequestParam String status, @AuthenticationPrincipal CustomOAuth2User principal) {
        return ResponseEntity.ok(applicationService.fetchAllApplications(status, principal.getUserId()));
    }

    @GetMapping("/penalties")
    public ResponseEntity<List<PenaltyProjection>> fetchAllPenaltiesForAuthenticatedUser() {
        return ResponseEntity.ok(penaltyService.fetchAllPenaltiesForAuthenticatedUser());
    }

    @GetMapping("/penalties/total")
    public int getTotalScore(@AuthenticationPrincipal CustomOAuth2User principal) {
        return penaltyService.getTotalScore(principal.getUserId());
    }

    @GetMapping("/points")
    public ResponseEntity<List<PointProjection>> fetchAllPointsHistory(@AuthenticationPrincipal CustomOAuth2User user,
                                                                       Pageable pageable) {
        return ResponseEntity.ok(pointService.fetchAllPointsHistoryByUserId(user.getUserId(), pageable));
    }

    @GetMapping("/points/total")
    public int fetchCurrentPointForAuthenticatedUser(@AuthenticationPrincipal CustomOAuth2User user) {
        return pointService.getCurrentPoints(user.getUserId());
    }

    @PostMapping("/points/withdrawals")
    @ResponseStatus(HttpStatus.CREATED)
    public void requestWithdrawal(@RequestBody @Valid PointWithdrawalRequest request, @AuthenticationPrincipal CustomOAuth2User user) {
        pointService.withdrawPoint(request, user.getUserId());
    }

    @GetMapping("/points/withdrawals")
    public ResponseEntity<List<WithdrawalProjection>> fetchAllWithdrawalHistory(@AuthenticationPrincipal CustomOAuth2User user,
                                                                                Pageable pageable) {
        return ResponseEntity.ok(pointService.fetchAllWithdrawalHistory(user.getUserId(), pageable));
    }

    @GetMapping("/inquiries")
    public ResponseEntity<List<InquiryProjection>> fetchAllInquiries(@AuthenticationPrincipal CustomOAuth2User principal,
                                                                     Pageable pageable) {
        return ResponseEntity.ok(inquiryService.fetchAllInquiriesByUserId(principal.getUserId(), pageable));
    }
}
