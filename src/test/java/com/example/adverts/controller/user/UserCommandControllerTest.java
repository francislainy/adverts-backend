package com.example.adverts.controller.user;

import com.example.adverts.model.dto.user.UserCreateDto;
import com.example.adverts.service.interfaces.user.UserCommandService;
import jwt.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.adverts.Utils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.slf4j.Logger;

@ComponentScan({"jwt"})
@WebMvcTest(value = UserCommandController.class, includeFilters = {
        // to include JwtUtil in spring context
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)})
class UserCommandControllerTest {

    Logger logger = LoggerFactory.getLogger(UserCommandControllerTest.class);

    @MockBean
    private UserCommandService userCommandService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    AuthenticationManager authenticationManager;

    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    public void setUp() {
        dummy = new User("user@email.com", "123456", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

    @Test
    void testCreateUserThrows403WhenNoAuthHeader() throws Exception {

        UserCreateDto userCreateDto = new UserCreateDto("", "test", "user@email.com", "user@email.com", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateUser() throws Exception {

        UUID userId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");

        UserCreateDto userCreateDto = new UserCreateDto("user", "test", "user@email.com", "user@email.com", "123456", "basic");
        UserCreateDto userCreateResponseDto = new UserCreateDto(userId, "user", "test", "user@email.com", "user@email.com", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);
        String jsonResponse = asJsonString(userCreateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(userCommandService.createUser(userCreateDto)).thenReturn(userCreateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstname").value(userCreateResponseDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(userCreateResponseDto.getLastname()))
                .andExpect(jsonPath("$.email").value(userCreateResponseDto.getEmail()))
                .andExpect(jsonPath("$.username").value(userCreateResponseDto.getUsername()))
                .andExpect(jsonPath("$.password").value(userCreateResponseDto.getPassword()))
                .andExpect(jsonPath("$.role").value(userCreateResponseDto.getRole()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateUserThrowsErrorWhenEmptyFirstname() throws Exception {

        UserCreateDto userCreateDto = new UserCreateDto("", "test", "user@email.com", "user@email.com", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateUserThrowsErrorWhenEmptyLastname() throws Exception {

        UserCreateDto userCreateDto = new UserCreateDto("user", "", "user@email.com", "user@email.com", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateUserThrowsErrorWhenEmptyEmail() throws Exception {

        UserCreateDto userCreateDto = new UserCreateDto("user", "test", "", "user@email.com", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateUserThrowsErrorWhenInvalidEmail() throws Exception {

        UserCreateDto userCreateDto = new UserCreateDto("user", "test", "invalid_email", "user@email.com", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateUserThrowsErrorWhenEmptyUsername() throws Exception {

        UserCreateDto userCreateDto = new UserCreateDto("user", "test", "user@email.com", "", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateUserThrowsErrorWhenInvalidUsername() throws Exception {

        UserCreateDto userCreateDto = new UserCreateDto("user", "test", "user@email.com", "invalid_username", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateUserThrowsErrorWhenEmptyPassword() throws Exception {

        UserCreateDto userCreateDto = new UserCreateDto("user", "test", "user@email.com", "user@email.com", "", "basic");

        String jsonCreate = asJsonString(userCreateDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testCreateUserDoesNotThrowErrorWhenEmptyRole() throws Exception {

        UUID userId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");
        UserCreateDto userCreateDto = new UserCreateDto("user", "test", "user@email.com", "user@email.com", "123456", "");
        UserCreateDto userCreateResponseDto = new UserCreateDto(userId, "user", "test", "user@email.com", "user@email.com", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);
        String jsonResponse = asJsonString(userCreateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(userCommandService.createUser(userCreateDto)).thenReturn(userCreateResponseDto);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstname").value(userCreateResponseDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(userCreateResponseDto.getLastname()))
                .andExpect(jsonPath("$.email").value(userCreateResponseDto.getEmail()))
                .andExpect(jsonPath("$.username").value(userCreateResponseDto.getUsername()))
                .andExpect(jsonPath("$.password").value(userCreateResponseDto.getPassword()))
                .andExpect(jsonPath("$.role").value(userCreateResponseDto.getRole()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testLoginReturnsJwt() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user@email.com", "123456");
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("anyToken");

        String jsonRequest = asJsonString(authenticationRequest);
        String jsonResponse = asJsonString(authenticationResponse);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user/login")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);

        when(jwtUtil.generateToken(dummy)).thenReturn("anyToken");
        when(userDetailsServiceImpl.loadUserByUsername(eq("user@email.com"))).thenReturn(dummy);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(jsonResponse, true))
                .andExpect(jsonPath("$.jwt").value(is(notNullValue())))
                .andExpect(jsonPath("$.jwt").value("anyToken"))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

}
