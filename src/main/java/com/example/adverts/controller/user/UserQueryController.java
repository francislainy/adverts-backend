package com.example.adverts.controller.user;

import com.example.adverts.model.dto.user.UserQueryDto;
import com.example.adverts.service.interfaces.user.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RequestMapping("/api/adverts/user")
@RestController
public class UserQueryController {

    @Autowired
    private UserQueryService userQueryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<UserQueryDto>> listAllUsers() {

        HashMap<String, List<UserQueryDto>> result = new HashMap<>();
        result.put("users", userQueryService.getAllUsers());
        return result;

    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserQueryDto> getUser(@PathVariable(value = "userId") UUID id) {
        return new ResponseEntity<>(userQueryService.getUser(id), HttpStatus.OK);
    }

}
