package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.Gender;
import com.iamjunhyeok.review.constant.Role;
import com.iamjunhyeok.review.dto.request.UserUpdateInfoRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "users")
public class User extends Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String password;

    private String name;

    @Column(length = 11)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @Column(length = 13)
    private String idNumber;

    private String bank;

    private String accountNumber;

    private String accountHolder;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String profileImageName;

    @OneToMany(mappedBy = "user")
    private List<Penalty> penalties = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Sns> sns = new ArrayList<>();

    public static User createUser(String email, String nickname, String password) {
        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setRole(Role.ROLE_USER);
        return user;
    }

    public void updatePassword(String newPassword) {
        this.setPassword(newPassword);
    }

    public User update(UserUpdateInfoRequest request) {
        this.email = request.getEmail();
        this.nickname = request.getNickname();
        this.name = request.getName();
        this.phoneNumber = request.getPhoneNumber();
        this.gender = request.getGender();
        this.birthDate = request.getBirthDate();
        this.address = request.getAddress();
        this.rest = request.getRest();
        this.postalCode = request.getPostalCode();
        return this;
    }

    public User updateProfileImageName(String newProfileImageName) {
        this.profileImageName = newProfileImageName;
        return this;
    }
}
