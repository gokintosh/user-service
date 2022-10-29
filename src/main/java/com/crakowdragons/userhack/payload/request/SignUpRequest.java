package com.crakowdragons.userhack.payload.request;

import com.crakowdragons.userhack.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 3,max = 20)
    private String username;

    @NotBlank
    @Size(min = 6,max = 70)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 8,max = 120)
    private String password;
}
