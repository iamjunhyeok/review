package com.iamjunhyeok.review.evaluator;

import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.repository.CampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@RequiredArgsConstructor
public class CampaignPermissionEvaluator implements PermissionEvaluator {

    private final CampaignRepository campaignRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        Long id = Long.valueOf(targetId.toString());
        Long userId = campaignRepository.findUserIdById(id);
        return principal.getUserId().equals(userId);
    }
}
