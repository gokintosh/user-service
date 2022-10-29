package com.crakowdragons.userhack.repository;

import com.crakowdragons.userhack.model.ERole;
import com.crakowdragons.userhack.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role>findByName(ERole name);
}
