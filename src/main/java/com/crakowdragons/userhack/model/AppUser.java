package com.crakowdragons.userhack.model;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<UserRole> userRoles;
}
