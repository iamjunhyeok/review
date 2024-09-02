package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.request.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.request.CampaignUpdateRequest;
import com.iamjunhyeok.review.dto.response.CampaignCreateResponse;
import com.iamjunhyeok.review.dto.response.CampaignUpdateResponse;
import com.iamjunhyeok.review.projection.CampaignSearchProjection;
import com.iamjunhyeok.review.projection.CampaignSummaryProjection;
import com.iamjunhyeok.review.projection.CampaignViewProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import com.iamjunhyeok.review.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/campaigns")
    public ResponseEntity<CampaignCreateResponse> create(@RequestPart @Valid CampaignCreateRequest request,
                                                         @RequestPart List<MultipartFile> files,
                                                         @AuthenticationPrincipal CustomOAuth2User principal) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(CampaignCreateResponse.from(campaignService.create(request, files, principal)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/campaigns/{id}")
    public ResponseEntity<CampaignUpdateResponse> update(@PathVariable Long id,
                                                         @RequestPart @Valid CampaignUpdateRequest request,
                                                         @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        return ResponseEntity.ok(CampaignUpdateResponse.from(campaignService.update(id, request, files)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/campaigns/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        campaignService.delete(id);
    }

    @GetMapping("/campaigns")
    public ResponseEntity<List<CampaignSearchProjection>> search(@RequestParam(required = false) String type,
                                                                 @RequestParam(required = false) String categories,
                                                                 @RequestParam(required = false) String socials,
                                                                 @RequestParam(required = false) String options,
                                                                 Pageable pageable,
                                                                 @RequestParam(required = false) String swlat,
                                                                 @RequestParam(required = false) String swlng,
                                                                 @RequestParam(required = false) String nelat,
                                                                 @RequestParam(required = false) String nelng
    ) {
        return ResponseEntity.ok(campaignService.search(type, categories, socials, options, pageable, swlat, swlng, nelat, nelng));
    }

    @GetMapping("/campaigns/{id}")
    public ResponseEntity<CampaignViewProjection> fetchById(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.fetchById(id));
    }

    @GetMapping("/campaigns/{id}/summary")
    public ResponseEntity<CampaignSummaryProjection> summary(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.summary(id));
    }

    @GetMapping("/users/me/campaigns")
    public ResponseEntity<List<UserCampaignSearchProjection>> fetchAuthenticatedUserCampaigns(@RequestParam String status) {
        return ResponseEntity.ok(campaignService.fetchAuthenticatedUserCampaigns(status));
    }

    @PostMapping("/campaigns/{id}/favourite")
    @ResponseStatus(HttpStatus.CREATED)
    public void addToFavourites(@PathVariable Long id, @AuthenticationPrincipal CustomOAuth2User principal) {
        campaignService.addToFavourites(id, principal);
    }

    @DeleteMapping("/campaigns/{id}/favourite")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromFavourites(@PathVariable Long id, @AuthenticationPrincipal CustomOAuth2User principal) {
        campaignService.removeFromFavourites(id, principal);
    }
}
