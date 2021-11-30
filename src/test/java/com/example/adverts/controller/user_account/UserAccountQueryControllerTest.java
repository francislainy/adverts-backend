package com.example.adverts.controller.user_account;

import com.example.adverts.model.dto.user_account.UserAccountQueryDto;
import com.example.adverts.service.interfaces.user_account.UserAccountQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserAccountQueryController.class)
class UserAccountQueryControllerTest {

    @MockBean
    private UserAccountQueryService userAccountQueryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllUserAccountsWhenOneItemOnly() throws Exception {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");

        UserAccountQueryDto userAccountQueryDto = new UserAccountQueryDto(userId, "fran", "campos", "fran@gmail.com", "fran@gmail.com", "123456", "admin");
        List<UserAccountQueryDto> categoryQueryDtoList = List.of(userAccountQueryDto);

        when(userAccountQueryService.getAllUserAccounts()).thenReturn(categoryQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/user")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        HashMap<String, List<UserAccountQueryDto>> result = new HashMap<>();
        result.put("users", categoryQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.users.size()").value(1))
                .andExpect(jsonPath("$.users[0].id").value(userId.toString()))
                .andExpect(jsonPath("$.users[0].firstname").value(userAccountQueryDto.getFirstname()))
                .andExpect(jsonPath("$.users[0].lastname").value(userAccountQueryDto.getLastname()))
                .andExpect(jsonPath("$.users[0].email").value(userAccountQueryDto.getEmail()))
                .andExpect(jsonPath("$.users[0].username").value(userAccountQueryDto.getUsername()))
                .andExpect(jsonPath("$.users[0].password").value(userAccountQueryDto.getPassword()))
                .andExpect(jsonPath("$.users[0].role").value(userAccountQueryDto.getRole()))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testGetAllUsersWhenTwoItems() throws Exception {

        UUID userId1 = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UUID userId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");

        UserAccountQueryDto userAccountQueryDto1 = new UserAccountQueryDto(userId1, "fran1", "campos1", "fra1n@gmail.com", "fran1@gmail.com", "123456", "admin");
        UserAccountQueryDto userAccountQueryDto2 = new UserAccountQueryDto(userId2, "fran2", "campos2", "fran2@gmail.com", "fran2@gmail.com", "223456", "basic");
        List<UserAccountQueryDto> userAccountQueryDtoList = List.of(userAccountQueryDto1, userAccountQueryDto2);

        when(userAccountQueryService.getAllUserAccounts()).thenReturn(userAccountQueryDtoList);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/user")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        HashMap<String, List<UserAccountQueryDto>> result = new HashMap<>();
        result.put("users", userAccountQueryDtoList);

        String json = asJsonString(result);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.users.size()").value(2))
                .andExpect(jsonPath("$.users[0].id").value(userId1.toString()))
                .andExpect(jsonPath("$.users[0].firstname").value(userAccountQueryDto1.getFirstname()))
                .andExpect(jsonPath("$.users[0].lastname").value(userAccountQueryDto1.getLastname()))
                .andExpect(jsonPath("$.users[0].email").value(userAccountQueryDto1.getEmail()))
                .andExpect(jsonPath("$.users[0].username").value(userAccountQueryDto1.getUsername()))
                .andExpect(jsonPath("$.users[0].password").value(userAccountQueryDto1.getPassword()))
                .andExpect(jsonPath("$.users[0].role").value(userAccountQueryDto1.getRole()))
                .andExpect(jsonPath("$.users[1].id").value(userId2.toString()))
                .andExpect(jsonPath("$.users[1].firstname").value(userAccountQueryDto2.getFirstname()))
                .andExpect(jsonPath("$.users[1].lastname").value(userAccountQueryDto2.getLastname()))
                .andExpect(jsonPath("$.users[1].email").value(userAccountQueryDto2.getEmail()))
                .andExpect(jsonPath("$.users[1].username").value(userAccountQueryDto2.getUsername()))
                .andExpect(jsonPath("$.users[1].password").value(userAccountQueryDto2.getPassword()))
                .andExpect(jsonPath("$.users[1].role").value(userAccountQueryDto2.getRole()))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    void testGetUserItem() throws Exception {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UserAccountQueryDto userAccountQueryDto = new UserAccountQueryDto(userId, "fran", "campos", "fran@gmail.com", "fran@gmail.com", "123456", "admin");

        when(userAccountQueryService.getUserAccount(userId)).thenReturn(userAccountQueryDto);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/adverts/user/{userId}", userId)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

        String json = asJsonString(userAccountQueryDto);
        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(json, true))
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.firstname").value(userAccountQueryDto.getFirstname()))
                .andExpect(jsonPath("$.lastname").value(userAccountQueryDto.getLastname()))
                .andExpect(jsonPath("$.email").value(userAccountQueryDto.getEmail()))
                .andExpect(jsonPath("$.username").value(userAccountQueryDto.getUsername()))
                .andExpect(jsonPath("$.password").value(userAccountQueryDto.getPassword()))
                .andExpect(jsonPath("$.role").value(userAccountQueryDto.getRole()))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
