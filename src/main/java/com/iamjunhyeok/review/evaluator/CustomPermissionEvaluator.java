package com.iamjunhyeok.review.evaluator;

import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.CampaignRepository;
import com.iamjunhyeok.review.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final InquiryRepository inquiryRepository;

    private final ApplicationRepository applicationRepository;

    private final CampaignRepository campaignRepository;

    private final String ROLE_PREFIX = "ROLE_";

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (authentication.getPrincipal() instanceof String) {
            return false;
        }
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_PREFIX.concat(permission.toString())));
        if (isAdmin) return true;

        PermissionEvaluator evaluator = null;
        if (targetType.equals("inquiry")) {
            evaluator = new InquiryPermissionEvaluator(inquiryRepository);
        } else if (targetType.equals("application")) {
            evaluator = new ApplicationPermissionEvaluator(applicationRepository);
        } else if (targetType.equals("campaign")) {
            evaluator = new CampaignPermissionEvaluator(campaignRepository);
        }
        return evaluator.hasPermission(authentication, targetId, targetType, permission);
    }
}
