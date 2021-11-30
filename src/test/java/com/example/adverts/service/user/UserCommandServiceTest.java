package com.example.adverts.service.user;

import com.example.adverts.model.dto.user.UserCreateDto;
import com.example.adverts.model.entity.user.User;
import com.example.adverts.repository.user.UserRepository;
import com.example.adverts.service.interfaces.user.UserCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserCommandService.class)
class UserCommandServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    private UserCommandService userCommandService;

    @Test
    void testCreateUser() {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        User userMocked = new User(userId, "fran", "campos", "fran@gmail.com", "fran@gmail.com", "123456", "basic");
        UserCreateDto userCreateDto = new UserCreateDto("fran", "campos", "fran@gmail.com", "fran@gmail.com", "123456", "basic");

        when(userRepository.save(any(User.class))).thenReturn(userMocked);

        UserCreateDto userCreateDto1 = userCommandService.createUser(userCreateDto);

        assertNotNull(userCreateDto1);
        assertEquals(userId, userCreateDto1.getId());
        assertEquals(userMocked.getFirstname(), userCreateDto1.getFirstname());
        assertEquals(userMocked.getLastname(), userCreateDto1.getLastname());
        assertEquals(userMocked.getEmail(), userCreateDto1.getEmail());
        assertEquals(userMocked.getUsername(), userCreateDto1.getUsername());
        assertEquals(userMocked.getPassword(), userCreateDto1.getPassword());
        assertEquals(userMocked.getRole(), userCreateDto1.getRole());
    }

}
