package com.aht.UserManagementService.repository;

import com.aht.UserManagementService.entity.User;
import com.aht.UserManagementService.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByUserId(Integer id);
    public User findByUsername(String username);
    public List<User> findAllByStatus(UserStatus status);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
