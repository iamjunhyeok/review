package com.iamjunhyeok.review.repository;

import com.iamjunhyeok.review.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_유효한테스트_찾기성공() {
        userRepository.save(User.createUser("jeonjhyeok@gmail.com", "jeonjhyeok", "1234"));
        userRepository.save(User.createUser("jeonjhyeok2@gmail.com", "jeonjhyeok2", "12345"));
        userRepository.save(User.createUser("jeonjhyeok3@gmail.com", "jeonjhyeok3", "123456"));

        Optional<User> user = userRepository.findByEmail("jeonjhyeok@gmail.com");
        assertTrue(user.isPresent());
        assertEquals(user.get().getEmail(), "jeonjhyeok@gmail.com");
        assertEquals(user.get().getPassword(), "1234");
    }

    @Test
    void findByEmail_찾을수없는이메일_빈옵셔널리턴() {
        userRepository.save(User.createUser("jeonjhyeok@gmail.com", "jeonjhyeok", "1234"));
        userRepository.save(User.createUser("jeonjhyeok2@gmail.com", "jeonjhyeok2", "12345"));
        userRepository.save(User.createUser("jeonjhyeok3@gmail.com", "jeonjhyeok3", "123456"));

        assertTrue(userRepository.findByEmail("jeonjhyeok4@gmail.com").isEmpty());
    }
}