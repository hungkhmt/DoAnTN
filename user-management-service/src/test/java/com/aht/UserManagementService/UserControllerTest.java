package com.aht.UserManagementService;


import com.aht.UserManagementService.controller.UserController;
import com.aht.UserManagementService.entity.Role;
import com.aht.UserManagementService.entity.User;
import com.aht.UserManagementService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role(1, Role.RoleName.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user = new User(1, "username1", "123456", "abc@gmail.com", "fullname", new Date(), roles);
        ReflectionTestUtils.setField(userController, "userService", userService);
    }

//    @Test
//    void testCreateUser() {
//        when(userService.createUser(any(User.class))).thenReturn(user);
//
//        User createdUser = userController.createUser(user);
//
//        assertNotNull(createdUser);
//        assertEquals(user, createdUser);
//
//        verify(userService, times(1)).createUser(any(User.class));
//    }

    @Test
    void testGetAllUsers() {
        List<User> userList = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(userList);

        List<User> result = userController.getAllUserWithRoles();

        assertNotNull(result);
        assertEquals(userList.size(), result.size());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        Integer userId = 1;
        when(userService.getUserById(userId)).thenReturn(user);

        User result = userController.getUserWithRoleById(userId);

        assertNotNull(result);
        assertEquals(user, result);

        verify(userService, times(1)).getUserById(userId);
    }

//    @Test
//    void testUpdateUser() {
//        Integer userId = 1;
//        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(user);
//
//        User updatedUser = userController.updateUser(userId, user);
//
//        assertNotNull(updatedUser);
//        assertEquals(user, updatedUser);
//
//        verify(userService, times(1)).updateUser(eq(userId), any(User.class));
//    }

    @Test
    void testDeleteUser() {
        Integer userId = 1;
        doNothing().when(userService).deleteUser(userId);

        userController.deleteUser(userId);

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testGrantRoles() {
        Integer userId = 1;
        List<Role.RoleName> roleNames = Arrays.asList(Role.RoleName.USER);
        doNothing().when(userService).grantRoles(eq(userId), anyList());

        ResponseEntity<String> responseEntity = userController.grantRoles(userId, roleNames);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(userService, times(1)).grantRoles(eq(userId), anyList());
    }

    @Test
    void testRevokeRoles() {
        Integer userId = 1;
        doNothing().when(userService).revokeRoles(userId);

        ResponseEntity<String> responseEntity = userController.revokeRoles(userId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(userService, times(1)).revokeRoles(userId);
    }
}
