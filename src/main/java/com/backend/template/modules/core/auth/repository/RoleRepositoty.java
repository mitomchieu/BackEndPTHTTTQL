package com.backend.template.modules.core.auth.repository;

import com.backend.template.modules.core.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositoty extends JpaRepository<Role, String> {
}
