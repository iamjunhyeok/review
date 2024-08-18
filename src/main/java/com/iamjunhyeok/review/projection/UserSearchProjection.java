package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.constant.Gender;
import com.iamjunhyeok.review.constant.Role;
import com.iamjunhyeok.review.domain.User;

import java.time.LocalDate;

@EntityView(User.class)
public interface UserSearchProjection {
    @IdMapping
    Long getId();

    String getEmail();

    String getNickname();

    String getName();

    String getPhoneNumber();

    Gender getGender();

    LocalDate getBirthDate();

    String getIdNumber();

    String getBank();

    String getAccountNumber();

    String getAccountHolder();

    Role getRole();

    String getProfileImageName();
}
