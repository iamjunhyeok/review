package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.CampaignStatus;
import com.iamjunhyeok.review.domain.Campaign;
import com.iamjunhyeok.review.domain.CampaignMission;
import com.iamjunhyeok.review.domain.CampaignImage;
import com.iamjunhyeok.review.domain.CampaignLink;
import com.iamjunhyeok.review.domain.Code;
import com.iamjunhyeok.review.dto.CampaignCodeDto;
import com.iamjunhyeok.review.dto.CampaignCreateRequest;
import com.iamjunhyeok.review.dto.CampaignImageNameProjection;
import com.iamjunhyeok.review.dto.CampaignLinkDto;
import com.iamjunhyeok.review.dto.CampaignSearchProjection;
import com.iamjunhyeok.review.dto.CampaignSummaryProjection;
import com.iamjunhyeok.review.dto.CampaignUpdateRequest;
import com.iamjunhyeok.review.dto.CampaignViewProjection;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.repository.CampaignCodeRepository;
import com.iamjunhyeok.review.repository.CampaignImageRepository;
import com.iamjunhyeok.review.repository.CampaignLinkRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import com.iamjunhyeok.review.repository.CodeRepository;
import com.iamjunhyeok.review.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    private final CampaignCodeRepository campaignCodeRepository;

    @Transactional
    public Campaign create(CampaignCreateRequest request, List<MultipartFile> files) throws IOException {
        Campaign campaign = campaignRepository.save(
                Campaign.builder()
                        .type(request.getType())
                        .category(request.getCategory())
                        .social(request.getSocial())
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
                        .mission(request.getMission())
                        .guide(request.getGuide())
                        .information(request.getInformation())
                        .address(request.getAddress())
                        .rest(request.getRest())
                        .postalCode(request.getPostalCode())
                        .longitude(request.getLongitude())
                        .latitude(request.getLatitude())
                        .status(request.getApplicationStartDate().isAfter(LocalDate.now()) ? CampaignStatus.PLANNED : CampaignStatus.ONGOING)
                        .storeInformation(request.getStoreInformation())
                        .build()
        );
        List<Code> missions = codeRepository.findAllByCodeIn(request.getMissionCodes().stream().map(CampaignCodeDto::getCode).toList());
        List<String> arguments = request.getMissionCodes().stream().map(CampaignCodeDto::getArguments).toList();
        campaign.addMission(missions, arguments);

        List<Code> options = codeRepository.findAllByCodeIn(request.getOptionCodes().stream().map(CampaignCodeDto::getCode).toList());
        campaign.addOption(options);

        List<CampaignLink> links = convertDtoToEntity(request.getLinks());
        campaign.addLink(links);

        Map<String, String> newFilenameMap = generateNewFilenameMap(files);
        List<CampaignImage> images = convertFileToEntityUsingMap(files, newFilenameMap);
        campaign.addImage(images);

        putObjectAllFiles(files, newFilenameMap);

        return campaign;
    }

    /**
     * 새로운 파일명으로 파일을 S3 에 putObject
     * @param files
     * @param newFilenameMap
     * @throws IOException
     */
    private void putObjectAllFiles(List<MultipartFile> files, Map<String, String> newFilenameMap) throws IOException {
        for (MultipartFile file : files) {
            s3Util.putObject(newFilenameMap.get(file.getOriginalFilename()), file);
        }
    }

    /**
     * originalFilename 으로 newFilenameMap 에서 새로운 파일명을 조회 후 이를 가지고 Entity 로 변환
     * @param files
     * @param newFilenameMap
     * @return
     */
    private static List<CampaignImage> convertFileToEntityUsingMap(List<MultipartFile> files, Map<String, String> newFilenameMap) {
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
        // 캠페인 기본정보 업데이트
        campaign.update(request);

        campaignCodeRepository.deleteAllByIdInBatch(request.getDeleteMissionIds());

        Map<Boolean, List<CampaignCodeDto>> collect1 = request.getMissionCodes().stream().collect(Collectors.partitioningBy(dto -> dto.getId() != null));
        List<CampaignCodeDto> existingMissions = collect1.get(true);
        List<CampaignCodeDto> newMissions = collect1.get(false);

        List<Code> missions = codeRepository.findAllByCodeIn(newMissions.stream().map(CampaignCodeDto::getCode).toList());
        List<String> arguments = newMissions.stream().map(CampaignCodeDto::getArguments).toList();
        campaign.addMission(missions, arguments);

        // 1. 캠페인 코드 ID 로 엔티티 조회
        List<CampaignMission> existingCampaignMissionEntities = campaignCodeRepository.findAllById(existingMissions.stream().map(CampaignCodeDto::getId).toList());

        // 2. 변경 내용을 Map 으로 변환하여 저장 (Key: 캠페인 코드 ID, Value: DTO)
        Map<Long, String> collect2 = existingMissions.stream().collect(Collectors.toMap(CampaignCodeDto::getId, CampaignCodeDto::getArguments));

        // 3. 반복문을 통해서 update 메소드 호출
        existingCampaignMissionEntities.forEach(entity -> entity.update(collect2.get(entity.getId())));


        campaignCodeRepository.deleteAllByIdInBatch(request.getDeleteOptionIds());

        List<Code> options = codeRepository.findAllByCodeIn(request.getOptionCodes().stream().map(CampaignCodeDto::getCode).toList());
        campaign.addOption(options);



        // 삭제 ID 리스트로 들어온 항목들 일괄 삭제
        campaignLinkRepository.deleteAllByIdInBatch(request.getDeleteLinkIds());

        // 신규 항목, 업데이트 항목 파티셔닝
        Map<Boolean, List<CampaignLinkDto>> collect = request.getLinks().stream()
                .collect(Collectors.partitioningBy(dto -> dto.getId() != null));

        List<CampaignLinkDto> existingLinks = collect.get(true);
        List<CampaignLinkDto> newLinks = collect.get(false);

        // 신규 항목 저장
        List<CampaignLink> links = newLinks.stream()
                .map(CampaignLinkDto::getUrl)
                .map(CampaignLink::of)
                .toList();
        campaign.addLink(links);

        // 업데이트 대상 엔티티 조회
        List<CampaignLink> existingLinkEntities = campaignLinkRepository.findAllById(existingLinks.stream()
                .map(CampaignLinkDto::getId)
                .toList());

        // 업데이트 대상 엔티티의 link 필드 변경을 위해 Map 으로 변경
        Map<Long, String> dtoMap = existingLinks.stream()
                .collect(Collectors.toMap(CampaignLinkDto::getId, CampaignLinkDto::getUrl));

        // link 필드 변경
        existingLinkEntities.forEach(link -> link.updateUrl(dtoMap.get(link.getId())));
        campaignLinkRepository.saveAll(existingLinkEntities);


        // S3 에서 삭제하기 위해 파일 이름 조회 (삭제하기 전 수행해야 함)
        List<String> names = campaignImageRepository.findByIdIn(request.getDeleteImageIds())
                .stream().map(CampaignImageNameProjection::getName)
                .toList();

        // 이미지 삭제
        campaignImageRepository.deleteAllByIdInBatch(request.getDeleteImageIds());

        // 새로운 이미지 추가
        Map<String, String> newFilenameMap = generateNewFilenameMap(files);
        List<CampaignImage> images = convertFileToEntityUsingMap(files, newFilenameMap);
        campaign.addImage(images);

        // S3 에서 삭제
        for (String deleteImageName : names) {
            s3Util.deleteObject(deleteImageName);
        }
        // S3 에 추가
        putObjectAllFiles(files, newFilenameMap);

        return campaign;
    }

    @Transactional
    public void delete(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());
        campaign.delete();
    }

    public List<CampaignSearchProjection> search(String type, String categories, String socials, String options, Pageable pageable, String swlat, String swlng, String nelat, String nelng) {
        return campaignRepository.fetchAll(type, categories, socials, options, pageable, swlat, swlng, nelat, nelng);
    }


    public CampaignViewProjection fetchById(Long id) {
        return campaignRepository.fetchById(id, CampaignViewProjection.class)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());
    }

    public CampaignSummaryProjection summary(Long id) {
        return campaignRepository.fetchById(id, CampaignSummaryProjection.class)
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.build());
    }
}
