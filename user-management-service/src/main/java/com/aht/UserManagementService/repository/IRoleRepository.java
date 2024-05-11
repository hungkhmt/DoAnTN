package com.aht.UserManagementService.repository;

import com.aht.UserManagementService.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    public Role findByRoleName(Role.RoleName role_name);
}
