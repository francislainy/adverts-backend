package com.example.adverts.service.user;

import com.example.adverts.JwtUtil;
import com.example.adverts.UserDetailsServiceImpl;
import com.example.adverts.model.dto.user.UserQueryDto;
import com.example.adverts.model.entity.user.User;
import com.example.adverts.repository.user.UserRepository;
import com.example.adverts.service.impl.user.UserQueryServiceImpl;
import com.example.adverts.service.interfaces.user.UserQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserQueryService.class)
class UserQueryServiceTest {

    @Mock
    UserRepository userRepository;

    @MockBean
    private UserQueryService userQueryService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    public void setUp() {
        dummy = new org.springframework.security.core.userdetails.User("foo@email.com", "foo", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

    @BeforeEach
    void initUseCase() {
        userQueryService = new UserQueryServiceImpl(userRepository);
    }

    @Test
    void testGetUser() {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        User userMocked = new User(userId, "fran1", "campos1", "fran1@gmail.com", "fran1@gmail.com", "123456", "admin");

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(userMocked));

        UserQueryDto userQueryDto = userQueryService.getUser(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        assertNotNull(userQueryDto);
        assertEquals(userMocked.getId(), userQueryDto.getId());
        assertEquals(userMocked.getFirstname(), userQueryDto.getFirstname());
        assertEquals(userMocked.getLastname(), userQueryDto.getLastname());
        assertEquals(userMocked.getEmail(), userQueryDto.getEmail());
        assertEquals(userMocked.getUsername(), userQueryDto.getUsername());
        assertEquals(userMocked.getPassword(), userQueryDto.getPassword());
        assertEquals(userMocked.getRole(), userQueryDto.getRole());
    }

    @Test
    void testGetMultipleUsers() {

        UUID userId1 = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UUID userId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");

        User userMocked1 = new User(userId1, "fran1", "campos1", "fran1@gmail.com", "fran1@gmail.com", "123456", "admin");
        User userMocked2 = new User(userId2, "fran2", "campos2", "fran2@gmail.com", "fran2@gmail.com", "223456", "basic");

        List<User> userMockedList = List.of(userMocked1, userMocked2);

        when(userRepository.findAll()).thenReturn(userMockedList);

        List<UserQueryDto> userQueryDto = userQueryService.getAllUsers();

        assertNotNull(userQueryDto);
        assertEquals(userMockedList.size(), userQueryDto.size());
        assertEquals(userMockedList.get(0).getId(), userQueryDto.get(0).getId());
        assertEquals(userMockedList.get(0).getFirstname(), userQueryDto.get(0).getFirstname());
        assertEquals(userMockedList.get(0).getLastname(), userQueryDto.get(0).getLastname());
        assertEquals(userMockedList.get(0).getEmail(), userQueryDto.get(0).getEmail());
        assertEquals(userMockedList.get(0).getUsername(), userQueryDto.get(0).getUsername());
        assertEquals(userMockedList.get(0).getPassword(), userQueryDto.get(0).getPassword());
        assertEquals(userMockedList.get(0).getRole(), userQueryDto.get(0).getRole());

        assertEquals(userMockedList.get(1).getId(), userQueryDto.get(1).getId());
        assertEquals(userMockedList.get(1).getFirstname(), userQueryDto.get(1).getFirstname());
        assertEquals(userMockedList.get(1).getLastname(), userQueryDto.get(1).getLastname());
        assertEquals(userMockedList.get(1).getEmail(), userQueryDto.get(1).getEmail());
        assertEquals(userMockedList.get(1).getUsername(), userQueryDto.get(1).getUsername());
        assertEquals(userMockedList.get(1).getPassword(), userQueryDto.get(1).getPassword());
        assertEquals(userMockedList.get(1).getRole(), userQueryDto.get(1).getRole());
    }

}
