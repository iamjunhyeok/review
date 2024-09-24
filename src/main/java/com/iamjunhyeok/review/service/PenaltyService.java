package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.PenaltyReason;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Penalty;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.PenaltyProjection;
import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.PenaltyRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;

    private final UserRepository userRepository;

    private final ApplicationRepository applicationRepository;

    @Transactional
    public void givePenaltyPoints(Long userId, Long applicationId, PenaltyReason reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());

        penaltyRepository.save(Penalty.of(user, application, reason));
    }

    @Transactional
    public void givePenaltyPoints(User user, Application application, PenaltyReason reason) {
        penaltyRepository.save(Penalty.of(user, application, reason));
    }

    @Transactional
    public void delete(Long userId, Long id) {
        penaltyRepository.findById(id)
                .orElseThrow(() -> ErrorCode.PENALTY_NOT_FOUND.build())
                .delete();
    }

    public List<PenaltyProjection> fetchAllPenaltyHistoryByUserId(Long userId, Pageable pageable) {
        return penaltyRepository.fetchAllPenaltyHistoryByUserId(userId, pageable);
    }

    public int getTotalScore(Long userId) {
        return penaltyRepository.getTotalScore(userId);
    }
}
