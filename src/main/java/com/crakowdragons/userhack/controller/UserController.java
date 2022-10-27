package com.crakowdragons.userhack.controller;


import com.crakowdragons.userhack.dto.AppUserDto;
import com.crakowdragons.userhack.dto.AppUserResponseDto;
import com.crakowdragons.userhack.model.AppUser;
import com.crakowdragons.userhack.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper mapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody AppUserDto appUserDto){

        userService.registerUser(mapper.map(appUserDto, AppUser.class));

    }


}
