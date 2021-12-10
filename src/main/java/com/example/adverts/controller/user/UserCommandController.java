package com.example.adverts.controller.user;

import com.example.adverts.*;
import com.example.adverts.model.dto.user.UserCreateDto;
import com.example.adverts.service.interfaces.user.UserCommandService;
import com.example.adverts.util.CustomExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RequestMapping("/api/adverts/user")
@RestController
public class UserCommandController extends CustomExceptionHandler {

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    public UserCommandController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {

        return new ResponseEntity<>(userCommandService.createUser(userCreateDto), HttpStatus.CREATED);
    }


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginUser(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username or password", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String jwt = jwtTokenUtil.generateToken(userDetails);
        System.out.println(jwt);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}

