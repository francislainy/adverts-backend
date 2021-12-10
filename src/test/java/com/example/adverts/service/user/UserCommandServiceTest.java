package com.example.adverts.service.user;

import com.example.adverts.JwtUtil;
import com.example.adverts.UserDetailsServiceImpl;
import com.example.adverts.TestConfig;
import com.example.adverts.model.dto.user.UserCreateDto;
import com.example.adverts.model.entity.user.User;
import com.example.adverts.repository.user.UserRepository;
import com.example.adverts.service.interfaces.user.UserCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import({TestConfig.class})
@WebMvcTest(UserCommandService.class)
class UserCommandServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    private UserCommandService userCommandService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void testCreateUser() {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        User userMocked = new User(userId, "fran", "campos", "fran@gmail.com", "123456", "fran@gmail.com", "basic");
        UserCreateDto userCreateDto = new UserCreateDto("fran", "campos", "fran@gmail.com", "fran@gmail.com", "123456", "basic");

        when(userRepository.save(any(User.class))).thenReturn(userMocked);

        userCreateDto = userCommandService.createUser(userCreateDto);

        assertNotNull(userCreateDto);
        assertEquals(userId, userCreateDto.getId());
        assertEquals(userMocked.getFirstname(), userCreateDto.getFirstname());
        assertEquals(userMocked.getLastname(), userCreateDto.getLastname());
        assertEquals(userMocked.getEmail(), userCreateDto.getEmail());
        assertEquals(userMocked.getUsername(), userCreateDto.getUsername());
        assertEquals(userMocked.getPassword(), userCreateDto.getPassword());
        assertEquals(userMocked.getRole(), userCreateDto.getRole());
    }

}
