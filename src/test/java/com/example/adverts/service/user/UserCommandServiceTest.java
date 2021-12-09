package com.example.adverts.service.user;

import com.example.adverts.JwtUtil;
import com.example.adverts.MyUserDetailsService;
import com.example.adverts.TestConfig;
import com.example.adverts.model.dto.user.UserCreateDto;
import com.example.adverts.model.entity.user.User;
import com.example.adverts.repository.user.UserRepository;
import com.example.adverts.service.interfaces.user.UserCommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Import({TestConfig.class})
@WebMvcTest(UserCommandService.class)
class UserCommandServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    private UserCommandService userCommandService;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationManagerBuilder auth;

    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    public void setUp() {
        dummy = new org.springframework.security.core.userdetails.User("foo@email.com", "foo", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }



    @Test
    void testCreateUser() throws Exception {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        User userMocked = new User(userId, "fran", "campos", "fran@gmail.com", "123456", "fran@gmail.com", "basic");
        UserCreateDto userCreateDto = new UserCreateDto("fran", "campos", "fran@gmail.com", "fran@gmail.com", "123456", "basic");

//        PasswordEncoderTest passwordEncoderTest = new PasswordEncoderTest();
//        passwordEncoderTest.encode("123456");

//        when(auth.userDetailsService(myUserDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance())).thenReturn(auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoderTest));
        when(myUserDetailsService.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
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
