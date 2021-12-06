package com.example.adverts.service.impl.user;

import com.example.adverts.model.dto.user.UserCreateDto;
import com.example.adverts.model.entity.user.User;
import com.example.adverts.repository.user.UserRepository;
import com.example.adverts.service.interfaces.user.UserCommandService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserCommandServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserCreateDto createUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setFirstname(userCreateDto.getFirstname());
        user.setLastname(userCreateDto.getLastname());
        user.setEmail(userCreateDto.getEmail());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());

        user = userRepository.save(user);

        return new UserCreateDto(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()), user.getRole());
    }
}
