package com.iamjunhyeok.review.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamjunhyeok.review.constant.Role;
import com.iamjunhyeok.review.constant.SnsType;
import com.iamjunhyeok.review.domain.CustomOAuth2User;
import com.iamjunhyeok.review.domain.Sns;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.dto.NaverOAuth2Response;
import com.iamjunhyeok.review.repository.SnsRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import static com.iamjunhyeok.review.dto.NaverOAuth2Response.Details;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final SnsRepository snsRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 어떤 인증 provider 인지
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String clientName = userRequest.getClientRegistration().getClientName();

        Long userId;
        Role role;
        if (registrationId.equals("naver")) {
            NaverOAuth2Response naverOAuth2Response = new ObjectMapper().convertValue(oAuth2User.getAttributes(), NaverOAuth2Response.class);

            Details response = naverOAuth2Response.getResponse();

            String snsId = response.getId();

            // SNS ID 로 사용자 찾기
            Sns sns = snsRepository.findBySnsId(snsId) // join 으로 찾기
                    .orElseGet(() -> {
                        User user = User.createUser(response.getEmail(), response.getNickname(), "PASSWORD");
                        userRepository.save(user);

                        return snsRepository.save(Sns.of(snsId, SnsType.NAVER, user));
                    });
            User user = sns.getUser();
            userId = user.getId();
            role = user.getRole();
        } else {
            return null;
        }
        return new CustomOAuth2User(userId, role);
    }
}
