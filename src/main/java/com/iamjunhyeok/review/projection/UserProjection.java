package com.iamjunhyeok.review.projection;

import lombok.Getter;

@Getter
public class UserProjection {
    private Long id;
    private String email;
    private String nickname;
    private String profileImageName;
}
