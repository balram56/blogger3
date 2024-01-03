package com.blog2.repository;

import com.blog2.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    //custom method
    Optional<Role> findByName(String name);
}
