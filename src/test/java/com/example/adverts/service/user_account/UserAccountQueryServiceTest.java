package com.example.adverts.service.user_account;

import com.example.adverts.model.dto.user_account.UserAccountQueryDto;
import com.example.adverts.model.entity.userinfo.UserAccount;
import com.example.adverts.repository.user_account.UserAccountRepository;
import com.example.adverts.service.impl.user_account.UserQueryServiceImpl;
import com.example.adverts.service.interfaces.user_account.UserAccountQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserAccountQueryService.class)
class UserAccountQueryServiceTest {

    @Mock
    UserAccountRepository userAccountRepository;

    @MockBean
    private UserAccountQueryService userAccountQueryService;

    @BeforeEach
    void initUseCase() {
        userAccountQueryService = new UserQueryServiceImpl(userAccountRepository);
    }

    @Test
    void testGetUser() {

        UUID userId = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UserAccount userMocked = new UserAccount(userId, "fran1", "campos1", "fran1@gmail.com", "fran1@gmail.com", "123456", "admin");

        when(userAccountRepository.findById(any(UUID.class))).thenReturn(Optional.of(userMocked));

        UserAccountQueryDto userAccountQueryDto = userAccountQueryService.getUserAccount(UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb"));

        assertNotNull(userAccountQueryDto);
        assertEquals(userMocked.getId(), userAccountQueryDto.getId());
        assertEquals(userMocked.getFirstname(), userAccountQueryDto.getFirstname());
        assertEquals(userMocked.getLastname(), userAccountQueryDto.getLastname());
        assertEquals(userMocked.getEmail(), userAccountQueryDto.getEmail());
        assertEquals(userMocked.getUsername(), userAccountQueryDto.getUsername());
        assertEquals(userMocked.getPassword(), userAccountQueryDto.getPassword());
        assertEquals(userMocked.getRole(), userAccountQueryDto.getRole());
    }

    @Test
    void testGetMultipleUsers() {

        UUID userId1 = UUID.fromString("02c903f7-7a55-470d-8449-cf7587f5a3fb");
        UUID userId2 = UUID.fromString("7bc5102a-31c5-1cc7-9b92-cbf0db865c89");

        UserAccount userMocked1 = new UserAccount(userId1, "fran1", "campos1", "fran1@gmail.com", "fran1@gmail.com", "123456", "admin");
        UserAccount userMocked2 = new UserAccount(userId2, "fran2", "campos2", "fran2@gmail.com", "fran2@gmail.com", "223456", "basic");

        List<UserAccount> userMockedList = List.of(userMocked1, userMocked2);

        when(userAccountRepository.findAll()).thenReturn(userMockedList);

        List<UserAccountQueryDto> userQueryDto = userAccountQueryService.getAllUserAccounts();

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
