package com.example.adverts.service.interfaces.user;

import com.example.adverts.model.dto.user.UserQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserQueryService {

    UserQueryDto getUser(UUID id);

    List<UserQueryDto> getAllUsers();

}
