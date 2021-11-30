package com.example.adverts.service.impl.user_account;

import com.example.adverts.model.dto.user_account.UserAccountQueryDto;
import com.example.adverts.model.entity.userinfo.UserAccount;
import com.example.adverts.repository.user_account.UserAccountRepository;
import com.example.adverts.service.interfaces.user_account.UserAccountQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserQueryServiceImpl implements UserAccountQueryService {

    private final UserAccountRepository userAccountRepository;

    public UserQueryServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }


    @Override
    public UserAccountQueryDto getUserAccount(UUID id) {
        if (userAccountRepository.findById(id).isPresent()) {

            UserAccount u = userAccountRepository.findById(id).get();

            return new UserAccountQueryDto(u.getId(), u.getFirstname(), u.getLastname(), u.getEmail(), u.getUsername(), u.getPassword(), u.getRole());

        } else {
            return null;
        }
    }

    @Override
    public List<UserAccountQueryDto> getAllUserAccounts() {

        List<UserAccountQueryDto> usersList = new ArrayList<>();

        userAccountRepository.findAll().forEach(u ->
                usersList.add(new UserAccountQueryDto(u.getId(), u.getFirstname(), u.getLastname(), u.getEmail(), u.getUsername(), u.getPassword(), u.getRole())));

        return usersList;
    }
}
