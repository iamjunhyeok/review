package com.iamjunhyeok.review.controller;

import com.iamjunhyeok.review.dto.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.CampaignCreateResponse;
import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import com.iamjunhyeok.review.dto.CampaignSummaryProjection;
import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import com.iamjunhyeok.review.dto.CampaignUpdateResponse;
import com.iamjunhyeok.review.dto.CampaignViewProjection;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping
    public ResponseEntity<CampaignCreateResponse> create(@RequestPart @Valid CampaignCreateRequest request,
                                                         @RequestPart List<MultipartFile> files) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(CampaignCreateResponse.from(campaignService.create(request, files)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CampaignUpdateResponse> update(@PathVariable Long id,
                                                         @RequestPart @Valid CampaignUpdateRequest request,
                                                         @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        return ResponseEntity.ok(CampaignUpdateResponse.from(campaignService.update(id, request, files)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        campaignService.delete(id);
    }

    @GetMapping
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

    @GetMapping("/{id}")
    public ResponseEntity<CampaignViewProjection> fetchById(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.fetchById(id));
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<CampaignSummaryProjection> summary(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.summary(id));
    }
}
