package com.cracowdragons.userhack.service;

import com.cracowdragons.userhack.model.AppUser;
import com.cracowdragons.userhack.model.ERole;
import com.cracowdragons.userhack.model.Role;
import com.cracowdragons.userhack.security.jwt.JwtUtils;
import com.cracowdragons.userhack.payload.request.LoginRequest;
import com.cracowdragons.userhack.payload.request.SignUpRequest;
import com.cracowdragons.userhack.repository.RoleRepository;
import com.cracowdragons.userhack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    public AppUser registerUser(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email already exists try signing in or user another email");
        }
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username already exists try signing in or use another username");
        }

        AppUser user = new AppUser();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> roles = signUpRequest.getRole();
        Set<Role> userRoles = new HashSet<>();

        if (roles.isEmpty()) {
            Role role = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found!!"));
            userRoles.add(role);
        } else {
            roles.forEach(roleSet -> {
                switch (roleSet) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(()
                                -> new RuntimeException("role not found!"));
                        userRoles.add(adminRole);
                        break;

                    case "mod":
                        Role moderatorRole = roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(() ->
                                new RuntimeException("Role not found!!")
                        );
                        userRoles.add(moderatorRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() ->
                                new RuntimeException("Role not found!!")
                        );
                        userRoles.add(userRole);
                }
            });
        }


        user.setRoles(userRoles);
        AppUser user1 = userRepository.save(user);

        return user1;
    }

    public String loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return jwt;


    }
}
