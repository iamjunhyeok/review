package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.CampaignImage;
import com.iamjunhyeok.review.domain.CampaignLink;
import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.CampaignLinkDto;
import com.iamjunhyeok.review.dto.CampaignMissionDto;
import com.iamjunhyeok.review.dto.CampaignOptionDto;
import com.iamjunhyeok.review.dto.CodeDto;
import com.iamjunhyeok.review.dto.request.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.request.CampaignUpdateRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.CampaignProjection;
import com.iamjunhyeok.review.projection.CampaignViewProjection;
import com.iamjunhyeok.review.projection.UserCampaignSearchProjection;
import com.iamjunhyeok.review.repository.CampaignImageRepository;
import com.iamjunhyeok.review.repository.CampaignLinkRepository;
import com.iamjunhyeok.review.repository.CampaignMissionRepository;
import com.iamjunhyeok.review.repository.CampaignOptionRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import com.iamjunhyeok.review.repository.CodeRepository;
import com.iamjunhyeok.review.repository.FavouriteRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import com.iamjunhyeok.review.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CampaignService {
    private final CampaignRepository campaignRepository;
    private final CampaignLinkRepository campaignLinkRepository;
    private final CampaignImageRepository campaignImageRepository;
    private final CodeRepository codeRepository;
    private final S3Util s3Util;
    private final CampaignMissionRepository campaignMissionRepository;
    private final CampaignOptionRepository campaignOptionRepository;
    private final UserRepository userRepository;
    private final FavouriteRepository favouriteRepository;

    @Transactional
    public Campaign create(CampaignCreateRequest request, List<MultipartFile> files, CustomOAuth2User principal) throws IOException {
        User user = userRepository.getReferenceById(principal.getUserId());

        Code typeCode = codeRepository.getReferenceById(request.getTypeCodeId());
        Code categoryCode = codeRepository.getReferenceById(request.getCategoryCodeId());
        Code socialCode = codeRepository.getReferenceById(request.getSocialCodeId());

        Campaign campaign = campaignRepository.save(
                Campaign.builder()
                        .typeCode(typeCode)
                        .categoryCode(categoryCode)
                        .socialCode(socialCode)
                        .title(request.getTitle())
                        .capacity(request.getCapacity())
                        .applicationStartDate(request.getApplicationStartDate())
                        .applicationEndDate(request.getApplicationEndDate())
                        .announcementDate(request.getAnnouncementDate())
                        .reviewStartDate(request.getReviewStartDate())
                        .reviewEndDate(request.getReviewEndDate())
                        .offering(request.getOffering())
                        .offeringSummary(request.getOfferingSummary())
                        .keyword(request.getKeyword())
                        .hashtag(request.getHashtag())
                        .guide(request.getGuide())
                        .information(request.getInformation())
                        .address(request.getAddress())
                        .rest(request.getRest())
                        .postalCode(request.getPostalCode())
                        .longitude(request.getLongitude())
                        .latitude(request.getLatitude())
                        .administrativeDistrictCode(request.getAdministrativeDistrictCode())
                        .status(CampaignStatus.INSPECTING)
                        .storeInformation(request.getStoreInformation())
                        .point(request.getPoint())
                        .user(user)
                        .build()
        );

        List<Code> missions = codeRepository.findAllById(request.getMissions().stream()
                .map(CampaignMissionDto::getCode)
                .map(CodeDto::getId)
                .toList());
        Map<Long, String> argumentsMap = request.getMissions().stream()
                .filter(dto -> Strings.isNotBlank(dto.getValue()))
                .collect(Collectors.toMap(dto -> dto.getCode().getId(), CampaignMissionDto::getValue));
        campaign.addMission(missions, argumentsMap);

        List<Code> options = codeRepository.findAllById(request.getOptions().stream()
                .map(CampaignOptionDto::getCode)
                .map(CodeDto::getId)
                .toList());
        campaign.addOption(options);

        List<CampaignLink> links = convertDtoToEntity(request.getLinks());
        campaign.addLink(links);

        List<CampaignImage> images = files.stream()
                .map(MultipartFile::getOriginalFilename)
                .map(CampaignImage::of)
                .toList();
        campaign.addImage(images);

        putObjectAllFiles(files);

        return campaign;
    }

    private void putObjectAllFiles(List<MultipartFile> files) throws IOException {
        if (CollectionUtils.isEmpty(files)) return;
        for (MultipartFile file : files) {
            s3Util.putObject(file);
        }
    }

    /**
     * originalFilename 으로 newFilenameMap 에서 새로운 파일명을 조회 후 이를 가지고 Entity 로 변환
     * @param files
     * @param newFilenameMap
     * @return
     */
    private static List<CampaignImage> convertFileToEntityUsingMap(List<MultipartFile> files, Map<String, String> newFilenameMap) {
        if (CollectionUtils.isEmpty(files)) return null;
        return files.stream()
                .map(multipartFile -> newFilenameMap.get(multipartFile.getOriginalFilename()))
                .map(CampaignImage::of)
                .toList();
    }

    /**
     * CampaignLinkDto -> CampaignLink 변환
     * @param dtos
     * @return
     */
    private static List<CampaignLink> convertDtoToEntity(List<CampaignLinkDto> dtos) {
        return dtos.stream()
                .map(CampaignLink::of)
                .toList();
    }

    /**
     * originalFilename 과 새로운 파일명 매핑을 위한 Map (Key: originalFilename, Value: randomUUID)
     * @param files
     * @return
     */
    private static Map<String, String> generateNewFilenameMap(List<MultipartFile> files) {
        if (CollectionUtils.isEmpty(files)) return null;
        return files.stream()
                .collect(Collectors.toMap(multipartFile -> multipartFile.getOriginalFilename(), multipartFile -> generateNewFilename(multipartFile)));

    }

    /**
     * 새로운 파일명 부여 (originalFilename.ext -> randomUUID.ext)
     * @param file
     * @return
     */
    private static String generateNewFilename(MultipartFile file) {
        return String.valueOf(UUID.randomUUID()).concat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
    }

    @Transactional
    public Campaign update(Long id, CampaignUpdateRequest request, List<MultipartFile> files) throws IOException {
        Campaign campaign = campaignRepository.findByIdWithLink(id)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());

        Code typeCode = codeRepository.getReferenceById(request.getTypeCodeId());
        Code categoryCode = codeRepository.getReferenceById(request.getCategoryCodeId());
        Code socialCode = codeRepository.getReferenceById(request.getSocialCodeId());

        // Code 엔티티를 제외한 캠페인 기본정보 업데이트
        campaign.update(typeCode, categoryCode, socialCode, request);

        // 캠페인에 대한 미션정보 모두 삭제
        campaignMissionRepository.deleteAllByCampaignId(id);

        List<Code> missions = codeRepository.findAllById(request.getMissions().stream()
                .map(CampaignMissionDto::getCode)
                .map(CodeDto::getId)
                .toList());
        Map<Long, String> argumentsMap = request.getMissions().stream()
                .filter(dto -> Strings.isNotBlank(dto.getValue()))
                .collect(Collectors.toMap(dto -> dto.getCode().getId(), CampaignMissionDto::getValue));
        campaign.addMission(missions, argumentsMap);


        campaignOptionRepository.deleteAllByCampaignId(id);

        List<Code> options = codeRepository.findAllById(request.getOptions().stream()
                .map(CampaignOptionDto::getCode)
                .map(CodeDto::getId)
                .toList());
        campaign.addOption(options);

        campaignLinkRepository.deleteAllByCampaignId(id);

        List<CampaignLink> links = convertDtoToEntity(request.getLinks());
        campaign.addLink(links);


        campaignImageRepository.deleteAllByNameIn(request.getDeleteImageNames());

        // 새로운 이미지 추가
        List<CampaignImage> images = Optional.ofNullable(files)
                .orElse(Collections.emptyList())
                .stream()
                .map(MultipartFile::getOriginalFilename)
                .map(CampaignImage::of)
                .toList();
        campaign.addImage(images);

        // S3 에서 삭제
        for (String deleteImageName : request.getDeleteImageNames()) {
            s3Util.deleteObject(deleteImageName);
        }

        // S3 에 추가
        putObjectAllFiles(files);

        return campaign;
    }

    @Transactional
    public void delete(Long id) {
        campaignRepository.findById(id)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build())
                .delete();
    }

    public List<CampaignProjection> fetchAll(Long typeCodeId, Long[] categoryCodeIds, Long[] socialCodeIds, Long[] optionCodeIds, Long regionCodeId, Pageable pageable, String swlat, String swlng, String nelat, String nelng) {
        return campaignRepository.fetchAll(typeCodeId, categoryCodeIds, socialCodeIds, optionCodeIds, regionCodeId, pageable, swlat, swlng, nelat, nelng);
    }

    public CampaignViewProjection fetchById(Long id) {
        return campaignRepository.fetchById(id, CampaignViewProjection.class)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());
    }

    public CampaignProjection summary(Long id) {
        return campaignRepository.fetchOne(id);
    }

    public List<UserCampaignSearchProjection> fetchAuthenticatedUserCampaigns(String status) {
        return campaignRepository.fetchAuthenticatedUserCampaigns(status);
    }

    @Transactional
    public void addToFavourites(Long id, CustomOAuth2User principal) {
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());
        Campaign campaign = campaignRepository.getReferenceById(id);
        user.favourite(campaign);
    }

    @Transactional
    public void removeFromFavourites(Long id, CustomOAuth2User principal) {
        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());
        Campaign campaign = campaignRepository.getReferenceById(id);
        user.removeFromFavourites(campaign);
    }

    public CampaignProjection fetchOneDetail(Long id) {
        return campaignRepository.fetchOneDetail(id);
    }
}
