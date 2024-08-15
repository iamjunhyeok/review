package com.iamjunhyeok.review.service;

import com.iamjunhyeok.review.constant.PointReason;
import com.iamjunhyeok.review.domain.Application;
import com.iamjunhyeok.review.domain.Point;
import com.iamjunhyeok.review.domain.User;
import com.iamjunhyeok.review.domain.Withdrawal;
import com.iamjunhyeok.review.dto.request.PointWithdrawalRequest;
import com.iamjunhyeok.review.exception.ErrorCode;
import com.iamjunhyeok.review.projection.PointProjection;
import com.iamjunhyeok.review.projection.WithdrawalProjection;
import com.iamjunhyeok.review.repository.ApplicationRepository;
import com.iamjunhyeok.review.repository.PointRepository;
import com.iamjunhyeok.review.repository.UserRepository;
import com.iamjunhyeok.review.repository.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {
    private final PointRepository pointRepository;

    private final UserRepository userRepository;

    private final ApplicationRepository applicationRepository;

    private final WithdrawalRepository withdrawalRepository;

    public List<PointProjection> getPoints(Long id) {
        return pointRepository.findByUserId(id);
    }

    public void givePoints(Long userId, Long applicationId, Integer amount, PointReason reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> ErrorCode.USER_NOT_FOUND.build());
        Application application = applicationRepository.findByIdAndUserId(applicationId, userId)
                .orElseThrow(() -> ErrorCode.APPLICATION_NOT_FOUND.build());

        Point point = Point.of(user, application, amount);
        pointRepository.save(point);
    }

    public void givePoints(User user, Application application, Integer amount, PointReason reason) {
        Point point = Point.of(user, application, amount);
        pointRepository.save(point);
    }

    public int getCurrentPoints(Long userId) {
        return pointRepository.getCurrentPointByUserId(userId);
    }

    @Transactional
    public void withdrawPoint(PointWithdrawalRequest request, Long userId) {
        User user = userRepository.getReferenceById(userId);
        int point = getCurrentPoints(userId);
        if (point >= request.getAmount()) {
            withdrawalRepository.save(Withdrawal.request(request, user));
        } else {
            throw ErrorCode.NOT_ENOUGH_POINT.build();
        }
    }

    public List<WithdrawalProjection> fetchAllWithdrawalHistory(Long userId) {
        return withdrawalRepository.fetchAllWithdrawalHistoryByUserId(userId);
    }
}
