package com.example.adverts.service.impl.user;

import com.example.adverts.model.dto.user.UserQueryDto;
import com.example.adverts.model.entity.user.User;
import com.example.adverts.repository.user.UserRepository;
import com.example.adverts.service.interfaces.user.UserQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserQueryDto getUser(UUID id) {
        if (userRepository.findById(id).isPresent()) {

            User u = userRepository.findById(id).get();

            return new UserQueryDto(u.getId(), u.getFirstname(), u.getLastname(), u.getEmail(), u.getUsername(), u.getPassword(), u.getRole());

        } else {
            return null;
        }
    }

    @Override
    public List<UserQueryDto> getAllUsers() {

        List<UserQueryDto> usersList = new ArrayList<>();

        userRepository.findAll().forEach(u ->
                usersList.add(new UserQueryDto(u.getId(), u.getFirstname(), u.getLastname(), u.getEmail(), u.getUsername(), u.getPassword(), u.getRole())));

        return usersList;
    }
}
