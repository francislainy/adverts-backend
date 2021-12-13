package com.example.adverts.service.interfaces.user;

import com.example.adverts.model.dto.user.UserCreateDto;
import org.springframework.stereotype.Service;

@Service
public interface UserCommandService {

    UserCreateDto createUser(UserCreateDto userCreateDto);

}
