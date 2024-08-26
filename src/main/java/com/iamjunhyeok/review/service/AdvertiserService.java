package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.AdvertiserStatus;
import com.iamjunhyeok.review.domain.Advertiser;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.request.AdvertiserApplyRequest;
import com.iamjunhyeok.review.repository.AdvertiserRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import com.iamjunhyeok.review.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdvertiserService {

    private final UserRepository userRepository;
    private final AdvertiserRepository advertiserRepository;
    private final S3Util s3Util;
    private static final String BUCKET_NAME = "olim-business-license";

    @Transactional
    public void apply(AdvertiserApplyRequest request, MultipartFile file, CustomOAuth2User principal) throws IOException {
        User user = userRepository.getReferenceById(principal.getUserId());
        Advertiser advertiser = Advertiser.builder()
                .businessTypeCode(request.getBusinessTypeCode())
                .businessName(request.getBusinessName())
                .businessNumber(request.getBusinessNumber())
                .businessLicenseImageName(file.getOriginalFilename())
                .homepageUrl(request.getHomepageUrl())
                .taxInvoiceEmail(request.getTaxInvoiceEmail())
                .accessSourceCode(request.getAccessSourceCode())
                .status(AdvertiserStatus.APPLIED)
                .user(user)
                .build();
        advertiserRepository.save(advertiser);

        s3Util.putObject(BUCKET_NAME, file);
    }
}
