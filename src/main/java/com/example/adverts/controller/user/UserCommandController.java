package com.example.adverts.controller.user;

import com.example.adverts.model.FeedbackMessage;
import com.example.adverts.model.dto.user.UserCreateDto;
import com.example.adverts.service.interfaces.user.UserCommandService;
import com.example.adverts.util.CustomExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RequestMapping("/api/adverts/user")
@RestController
public class UserCommandController extends CustomExceptionHandler {

    @Autowired
    private UserCommandService userCommandService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {

        if (userCreateDto.getEmail() != null && userCreateDto.getUsername() != null && userCreateDto.getPassword() != null) {
            return new ResponseEntity<>(userCommandService.createUser(userCreateDto), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new FeedbackMessage("Missing mandatory field"), HttpStatus.BAD_REQUEST);
        }

    }

}

