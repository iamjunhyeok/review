package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.WithdrawalStatus;
import com.iamjunhyeok.review.dto.request.PointWithdrawalRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Withdrawal extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountHolder;

    @Column(nullable = false)
    private String idNumber;

    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WithdrawalStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Withdrawal request(PointWithdrawalRequest request, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setBank(request.getBank());
        withdrawal.setAccountNumber(request.getAccountNumber());
        withdrawal.setAccountHolder(request.getAccountHolder());
        withdrawal.setIdNumber(request.getIdNumber());
        withdrawal.setAmount(request.getAmount());
        withdrawal.setStatus(WithdrawalStatus.REQUESTED);
        withdrawal.setUser(user);
        return withdrawal;
    }
}
