package com.crakowdragons.userhack.dto;


import com.crakowdragons.userhack.model.UserRole;
import lombok.Data;

import java.util.List;


@Data
public class AppUserDto {

    private String username;

    private String email;

    private String password;

    private List<UserRole> userRoles;
}
