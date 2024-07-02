package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.CampaignCreateResponse;
import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import com.iamjunhyeok.review.dto.CampaignUpdateResponse;
import com.iamjunhyeok.review.dto.CampaignViewResponse;
import com.iamjunhyeok.review.service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping
    public ResponseEntity<CampaignCreateResponse> create(@RequestBody @Valid CampaignCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(CampaignCreateResponse.from(campaignService.create(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CampaignUpdateResponse> update(@PathVariable Long id, @RequestBody @Valid CampaignUpdateRequest request) {
        return ResponseEntity.ok(CampaignUpdateResponse.from(campaignService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        campaignService.delete(id);
    }

    @GetMapping
    public ResponseEntity<List<CampaignSearchProjection>> search(@RequestParam(required = false) String type,
                                                                 @RequestParam(required = false) String category,
                                                                 @RequestParam(required = false) String filter,
                                                                 Pageable pageable,
                                                                 @RequestParam(required = false) String swlat,
                                                                 @RequestParam(required = false) String swlng,
                                                                 @RequestParam(required = false) String nelat,
                                                                 @RequestParam(required = false) String nelng
                                                                 ) {
        return ResponseEntity.ok(campaignService.search(type, category, filter, pageable, swlat, swlng, nelat, nelng));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignViewResponse> view(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.view(id));
    }
}
