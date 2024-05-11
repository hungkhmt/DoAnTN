package com.aht.UserManagementService;


import com.aht.UserManagementService.entity.Role;
import com.aht.UserManagementService.entity.User;
import com.aht.UserManagementService.repository.IUserRepository;
import com.aht.UserManagementService.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role(1, Role.RoleName.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user = new User(1, "username1", "123456", "abc@gmail.com", "fullname", new Date(), roles);
        ReflectionTestUtils.setField(userService, "userRepository", userRepository);
    }

//    @Test
//    public void testSaveUser() {
//        // Arrange
//        User user = new User();
//        user.setUsername("testuser");
//        when(userRepository.save(user)).thenReturn(user);
//
//        // Act
//        User savedUser = userService.createUser(user);
//
//        // Assert
//        assertNotNull(savedUser);
//        assertEquals("testuser", savedUser.getUsername());
//        verify(userRepository, times(1)).save(user);
//    }

    @Test
    public void testFindAllUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> foundUsers = userService.getAllUsers();

        // Assert
        assertFalse(foundUsers.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

}