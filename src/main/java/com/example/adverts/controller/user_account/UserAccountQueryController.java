package com.example.adverts.controller.user_account;

import com.example.adverts.model.dto.user_account.UserAccountQueryDto;
import com.example.adverts.service.interfaces.user_account.UserAccountQueryService;
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
public class UserAccountQueryController {

    @Autowired
    private UserAccountQueryService userAccountQueryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<UserAccountQueryDto>> listAllUsers() {

        HashMap<String, List<UserAccountQueryDto>> result = new HashMap<>();
        result.put("users", userAccountQueryService.getAllUserAccounts());
        return result;

    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserAccountQueryDto> getUser(@PathVariable(value = "userId") UUID id) {
        return new ResponseEntity<>(userAccountQueryService.getUserAccount(id), HttpStatus.OK);
    }

}
