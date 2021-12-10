package com.example.adverts.controller.user;

import com.example.adverts.JwtUtil;
import com.example.adverts.UserDetailsServiceImpl;
import com.example.adverts.model.dto.user.UserQueryDto;
import com.example.adverts.service.interfaces.user.UserQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserQueryController.class, includeFilters = {
        // to include JwtUtil in spring context
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtUtil.class)})
class UserQueryControllerTest {

    Logger logger = LoggerFactory.getLogger(UserQueryController.class);

    @MockBean
    private UserQueryService userQueryService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    public void setUp() {
        dummy = new User("foo@email.com", "foo", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

    @Test
    void testGetAllUsersThrows403WhenNoAuthHeader() throws Exception {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");

        UserQueryDto userQueryDto = new UserQueryDto(userId, "user", "test", "user@email.com", "user@email.com", "123456", "admin");
        List<UserQueryDto> categoryQueryDtoList = List.of(userQueryDto);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(userQueryService.getAllUsers()).thenReturn(categoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/user")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetAllUsersWhenOneItemOnly() throws Exception {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");

        UserQueryDto userQueryDto = new UserQueryDto(userId, "user", "test", "user@email.com", "user@email.com", "123456", "admin");
        List<UserQueryDto> categoryQueryDtoList = List.of(userQueryDto);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(userQueryService.getAllUsers()).thenReturn(categoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, List<UserQueryDto>> result = new HashMap<>();
        result.put("users", categoryQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.users.size()").value(1))
                .andExpect(jsonPath("$.users[0].id").value(userId.toString()))
                .andExpect(jsonPath("$.users[0].firstname").value(userQueryDto.getFirstname()))
                .andExpect(jsonPath("$.users[0].lastname").value(userQueryDto.getLastname()))
                .andExpect(jsonPath("$.users[0].email").value(userQueryDto.getEmail()))
                .andExpect(jsonPath("$.users[0].username").value(userQueryDto.getUsername()))
                .andExpect(jsonPath("$.users[0].password").value(userQueryDto.getPassword()))
                .andExpect(jsonPath("$.users[0].role").value(userQueryDto.getRole()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetAllUsersWhenTwoItems() throws Exception {

        UUID userId1 = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UUID userId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");

        UserQueryDto userQueryDto1 = new UserQueryDto(userId1, "user1", "test1", "user1@gmail.com", "user1@gmail.com", "123456", "admin");
        UserQueryDto userQueryDto2 = new UserQueryDto(userId2, "user2", "test2", "user2@gmail.com", "user2@gmail.com", "223456", "basic");
        List<UserQueryDto> userQueryDtoList = List.of(userQueryDto1, userQueryDto2);

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(userQueryService.getAllUsers()).thenReturn(userQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/user")
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        HashMap<String, List<UserQueryDto>> result = new HashMap<>();
        result.put("users", userQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.users.size()").value(2))
                .andExpect(jsonPath("$.users[0].id").value(userId1.toString()))
                .andExpect(jsonPath("$.users[0].firstname").value(userQueryDto1.getFirstname()))
                .andExpect(jsonPath("$.users[0].lastname").value(userQueryDto1.getLastname()))
                .andExpect(jsonPath("$.users[0].email").value(userQueryDto1.getEmail()))
                .andExpect(jsonPath("$.users[0].username").value(userQueryDto1.getUsername()))
                .andExpect(jsonPath("$.users[0].password").value(userQueryDto1.getPassword()))
                .andExpect(jsonPath("$.users[0].role").value(userQueryDto1.getRole()))
                .andExpect(jsonPath("$.users[1].id").value(userId2.toString()))
                .andExpect(jsonPath("$.users[1].firstname").value(userQueryDto2.getFirstname()))
                .andExpect(jsonPath("$.users[1].lastname").value(userQueryDto2.getLastname()))
                .andExpect(jsonPath("$.users[1].email").value(userQueryDto2.getEmail()))
                .andExpect(jsonPath("$.users[1].username").value(userQueryDto2.getUsername()))
                .andExpect(jsonPath("$.users[1].password").value(userQueryDto2.getPassword()))
                .andExpect(jsonPath("$.users[1].role").value(userQueryDto2.getRole()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetUserItemThrows403WhenNoAuthHeader() throws Exception {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UserQueryDto userQueryDto = new UserQueryDto(userId, "user", "test", "user@email.com", "user@email.com", "123456", "admin");

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(userQueryService.getUser(userId)).thenReturn(userQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/user/{userId}", userId)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testGetUserItem() throws Exception {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UserQueryDto userQueryDto = new UserQueryDto(userId, "user", "test", "user@email.com", "user@email.com", "123456", "admin");

        when(userDetailsServiceImpl.loadUserByUsername(eq("foo@email.com"))).thenReturn(dummy);
        when(userQueryService.getUser(userId)).thenReturn(userQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/user/{userId}", userId)
                .header("Authorization", "Bearer " + jwtToken)
                .accept(MediaType.APPLICATION_JSON);

        String json = asJsonString(userQueryDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstname").value(userQueryDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(userQueryDto.getLastname()))
                .andExpect(jsonPath("$.email").value(userQueryDto.getEmail()))
                .andExpect(jsonPath("$.username").value(userQueryDto.getUsername()))
                .andExpect(jsonPath("$.password").value(userQueryDto.getPassword()))
                .andExpect(jsonPath("$.role").value(userQueryDto.getRole()))
                .andReturn();

        logger.info(mvcResult.getResponse().getContentAsString());
    }

}
