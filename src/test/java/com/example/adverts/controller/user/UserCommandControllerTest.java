package com.example.adverts.controller.user;

import com.example.adverts.model.dto.category.CategoryCreateDto;
import com.example.adverts.model.dto.user.UserCreateDto;
import com.example.adverts.repository.user.UserRepository;
import com.example.adverts.service.interfaces.user.UserCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static com.example.adverts.Utils.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserCommandController.class)
class UserCommandControllerTest {

    @MockBean
    private UserCommandService userCommandService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCreateUser() throws Exception {

        UUID userId = UUID.fromString("2da4002a-31c5-4cc7-9b92-cbf0db998c41");

        UserCreateDto userCreateDto = new UserCreateDto("fran", "campos", "fran@gmail.com", "fran@gmail.com", "123456", "basic");
        UserCreateDto userCreateResponseDto = new UserCreateDto(userId, "fran", "campos", "fran@gmail.com", "fran@gmail.com", "123456", "basic");

        String jsonCreate = asJsonString(userCreateDto);
        String jsonResponse = asJsonString(userCreateResponseDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/adverts/user")
                .content(jsonCreate)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andReturn();

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

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


//    @Test
//    void testCreateCategoryThrowsErrorWhenTitleDoesNotExist() throws Exception {
//
//        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
//        String jsonCreate = asJsonString(categoryCreateDto);
//
//        RequestBuilder request = MockMvcRequestBuilders
//                .post("/api/adverts/category")
//                .content(jsonCreate)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON);
//        mockMvc.perform(request).andReturn();
//
//        when(userCommandService.createCategory(categoryCreateDto)).thenReturn(null);
//
//        MvcResult mvcResult = mockMvc.perform(request)
//                .andExpect(status().isBadRequest())
//                .andExpect(content().json("{\"message\": \"Missing title\"}", true))
//                .andReturn();
//
//
//        System.out.println(mvcResult.getResponse().getContentAsString());
//    }

}
