package com.example.adverts.service.interfaces.user_account;

import com.example.adverts.model.dto.user_account.UserAccountQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserAccountQueryService {

    UserAccountQueryDto getUserAccount(UUID id);

    List<UserAccountQueryDto> getAllUserAccounts();

}
