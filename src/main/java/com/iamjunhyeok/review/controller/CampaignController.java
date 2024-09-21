package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.dto.request.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.request.CampaignUpdateRequest;
import com.iamjunhyeok.review.projection.CampaignProjection;
import com.iamjunhyeok.review.projection.CampaignSummaryProjection;
import com.iamjunhyeok.review.projection.CampaignViewProjection;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'ADVERTISER')")
    @PostMapping("/campaigns")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestPart @Valid CampaignCreateRequest request,
                       @RequestPart List<MultipartFile> files,
                       @AuthenticationPrincipal CustomOAuth2User principal) throws IOException {
        campaignService.create(request, files, principal);
    }

    @PreAuthorize("hasPermission(#id, 'campaign', 'ADMIN')")
    @PatchMapping("/campaigns/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,
                       @RequestPart @Valid CampaignUpdateRequest request,
                       @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        campaignService.update(id, request, files);
    }

    @PreAuthorize("hasPermission(#id, 'campaign', 'ADMIN')")
    @DeleteMapping("/campaigns/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        campaignService.delete(id);
    }

    @GetMapping("/campaigns")
    public ResponseEntity<List<CampaignProjection>> fetchAll(@RequestParam(value = "type", required = false) Long typeCodeId,
                                                           @RequestParam(value = "categories", required = false) Long[] categoryCodeIds,
                                                           @RequestParam(value = "socials", required = false) Long[] socialCodeIds,
                                                           @RequestParam(value = "options", required = false) Long[] optionCodeIds,
                                                           @RequestParam(value = "region", required = false) Long regionCodeId,
                                                           Pageable pageable,
                                                           @RequestParam(required = false) String swlat,
                                                           @RequestParam(required = false) String swlng,
                                                           @RequestParam(required = false) String nelat,
                                                           @RequestParam(required = false) String nelng
    ) {
        return ResponseEntity.ok(campaignService.fetchAll(typeCodeId, categoryCodeIds, socialCodeIds, optionCodeIds, regionCodeId, pageable, swlat, swlng, nelat, nelng));
    }

    @GetMapping("/campaigns/{id}")
    public ResponseEntity<CampaignViewProjection> fetchById(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.fetchById(id));
    }

    @GetMapping("/campaigns/{id}/summary")
    public ResponseEntity<CampaignSummaryProjection> summary(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.summary(id));
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
