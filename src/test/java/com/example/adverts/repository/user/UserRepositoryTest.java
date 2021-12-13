package com.example.adverts.repository.user;

import com.example.adverts.model.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findOne() {
        User user = new User();
        user.setFirstname("user");
        user = userRepository.save(user);
        User fetchedUser = userRepository.findById(user.getId()).get();
        assertNotNull(fetchedUser);
    }


    @Test
    void findAll() {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        List<User> users = List.of(
                new User(userId, "user", "test", "user@email.com", "user@email.com", "123456", "admin")
        );
        userRepository.saveAll(users);
        List<User> userList = (List<User>) userRepository.findAll();
        assertTrue(userList.size() > 0);
    }

}
