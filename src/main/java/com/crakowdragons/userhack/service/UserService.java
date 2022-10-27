package com.crakowdragons.userhack.service;

import com.crakowdragons.userhack.model.AppUser;
import com.crakowdragons.userhack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(AppUser appUser){
        if(userRepository.findByUsername(appUser.getUsername())!=null){
            throw new RuntimeException("User with username "+appUser.getUsername()+" already exists!!");
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        userRepository.save(appUser);

    }
}
