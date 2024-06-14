package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.dto.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.CampaignCreateResponse;
import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import com.iamjunhyeok.review.dto.CampaignUpdateResponse;
import com.iamjunhyeok.review.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campaigns")
public class CampaignController {
    private final CampaignService campaignService;

    @PostMapping
    public ResponseEntity<CampaignCreateResponse> create(@RequestBody @Valid CampaignCreateRequest request) {
        Campaign campaign = campaignService.create(request);
        return ResponseEntity.created(
                UriComponentsBuilder.fromPath("/campaigns/{id}")
                        .buildAndExpand(campaign.getId())
                        .toUri()
        ).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CampaignUpdateResponse> update(@PathVariable Long id, @RequestBody @Valid CampaignUpdateRequest request) {
        return ResponseEntity.ok(CampaignUpdateResponse.from(campaignService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        campaignService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
