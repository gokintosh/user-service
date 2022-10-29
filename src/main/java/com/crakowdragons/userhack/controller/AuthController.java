package com.crakowdragons.userhack.controller;


import com.crakowdragons.userhack.payload.request.LoginRequest;
import com.crakowdragons.userhack.payload.request.SignUpRequest;
import com.crakowdragons.userhack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){

        userService.registerUser(signUpRequest);

        return ResponseEntity.ok("User registered successfully!!");
    }

    @PostMapping("/signin")
    public String loginUser(@Valid @RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);

    }
}
