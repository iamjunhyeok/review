package com.iamjunhyeok.review.domain;

import com.iamjunhyeok.review.constant.Gender;
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

    private LocalDate dateOfBirth;

    @Column(length = 13)
    private String idNumber;

    private String bank;

    private String accountNumber;

    private String accountHolder;

    @OneToMany(mappedBy = "user")
    private List<Penalty> penalties = new ArrayList<>();

    public static User createUser(String email, String nickname, String password) {
        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(password);
        return user;
    }

    public void updatePassword(String newPassword) {
        this.setPassword(newPassword);
    }

    public void updateInfo(String nickname, String address, String rest, String postalCode) {
        this.setNickname(nickname);
        this.setAddress(address);
        this.setRest(rest);
        this.setPostalCode(postalCode);
    }
}
