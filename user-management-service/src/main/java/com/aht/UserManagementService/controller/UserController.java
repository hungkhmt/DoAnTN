package com.aht.UserManagementService.controller;

import com.aht.UserManagementService.dto.ApiResponse;
import com.aht.UserManagementService.dto.UserDTO;
import com.aht.UserManagementService.entity.Role;
import com.aht.UserManagementService.entity.User;
import com.aht.UserManagementService.form.user.CreateUserForAdminForm;
import com.aht.UserManagementService.form.user.CreateUserForm;
import com.aht.UserManagementService.form.user.UpdateUserForm;
import com.aht.UserManagementService.form.user.UpdateUserPasswordForm;
import com.aht.UserManagementService.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@Validated
@Slf4j
public class UserController {
    @Autowired
    IUserService userService;


    @PostMapping("/registration")
    public ApiResponse<Void> createUser(@RequestBody @Valid CreateUserForm form) {
        userService.createUser(form);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/ad")
    public ApiResponse<Void> createUserFromAdmin(@RequestBody @Valid CreateUserForAdminForm form) {
        userService.createUserFromAdmin(form);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping()
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(userToUserDTO(user));
        }
        return userDTOS;
    }

    @GetMapping("/ad/role")
    public List<User> getAllUserWithRoles() {
        return userService.getAllUsers();
    }

    @GetMapping("/ad/active")
    public List<User> getAllUserActive() {
        return userService.getAllUserActive();
    }

    @GetMapping("/ad/disable")
    public List<User> getAllUserDisable() {
        return userService.getAllUserDisable();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable(name = "id") Integer id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userToUserDTO(user);
        return userDTO;
    }

    @GetMapping("/ad/role/{id}")
    public User getUserWithRoleById(@PathVariable(name = "id") Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping()
    public ApiResponse<Void> updateUser(@RequestBody @Valid UpdateUserForm form) {
        userService.updateUser(form);
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable(name = "id") Integer id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<?> updateUserPassword(@PathVariable(name = "id") Integer id, @RequestBody UpdateUserPasswordForm form) {
        userService.updatePassword(id, form);
        return ResponseEntity.status(HttpStatus.OK).body("Update password succesful");
    }

    @PostMapping("/ad/grant/{id}")
    public ApiResponse<Void> grantRoles(@PathVariable(name = "id") Integer userId, @RequestParam List<Role.RoleName> roleNames) {
        userService.grantRoles(userId, roleNames);
        return ApiResponse.<Void>builder().build();
    }

    @DeleteMapping("/ad/revoke/{id}")
    public ApiResponse<Void> revokeRoles(@PathVariable(name = "id") Integer userId) {
        userService.revokeRoles(userId);
        return ApiResponse.<Void>builder().build();
    }

    private UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullname(user.getFullname());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setAddress(user.getAddress());
        userDTO.setCreated_at(user.getCreated_at());

        return userDTO;
    }
}
