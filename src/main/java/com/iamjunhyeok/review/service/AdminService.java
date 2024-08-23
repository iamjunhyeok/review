package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.domain.Banner;
import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.dto.request.BannerRegisterRequest;
import com.iamjunhyeok.review.repository.BannerRepository;
import com.iamjunhyeok.review.repository.CodeRepository;
import com.iamjunhyeok.review.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final CodeRepository codeRepository;
    private final BannerRepository bannerRepository;

    private final S3Util s3Util;

    @Transactional
    public void registerBanner(Long codeId, BannerRegisterRequest request, MultipartFile file) throws IOException {
        Code code = codeRepository.getReferenceById(codeId);

        Banner banner = Banner.builder()
                .imageName(file.getOriginalFilename())
                .linkUrl(request.getLinkUrl())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .order(request.getOrder())
                .code(code)
                .build();
        bannerRepository.save(banner);

        s3Util.putObject(file);
    }
}
