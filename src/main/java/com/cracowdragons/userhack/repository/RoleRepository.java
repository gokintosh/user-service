package com.cracowdragons.userhack.repository;

import com.cracowdragons.userhack.model.ERole;
import com.cracowdragons.userhack.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role>findByName(ERole name);
}
