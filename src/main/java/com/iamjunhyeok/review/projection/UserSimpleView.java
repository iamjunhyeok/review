package com.iamjunhyeok.review.projection;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.iamjunhyeok.review.domain.User;

@EntityView(User.class)
public interface UserSimpleView {

    @IdMapping
    Long getId();

    String getEmail();

    String getNickname();

    String getProfileImageName();
}
