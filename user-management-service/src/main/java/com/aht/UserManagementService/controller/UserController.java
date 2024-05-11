package com.aht.UserManagementService.controller;

import com.aht.UserManagementService.dto.UserDTO;
import com.aht.UserManagementService.entity.Role;
import com.aht.UserManagementService.entity.User;
import com.aht.UserManagementService.form.user.CreateUserForAdminForm;
import com.aht.UserManagementService.form.user.CreateUserForm;
import com.aht.UserManagementService.form.user.UpdateUserForm;
import com.aht.UserManagementService.form.user.UpdateUserPasswordForm;
import com.aht.UserManagementService.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
@Validated
public class UserController {
    @Autowired
    IUserService userService;


    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserForm form) {
        userService.createUser(form);
        return ResponseEntity.ok("Create Successfully!");
    }

    @PostMapping("/ad")
    public ResponseEntity<String> createUserFromAdmin(@RequestBody @Valid CreateUserForAdminForm form) {
        userService.createUserFromAdmin(form);
        return ResponseEntity.ok("Create Successfully!");
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
    public ResponseEntity<String> updateUser(@RequestBody @Valid UpdateUserForm form) {
        userService.updateUser(form);
        return ResponseEntity.ok("Update Successfully!");
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") Integer id) {
        userService.deleteUser(id);
    }

    @PutMapping("/password/{id}")
    public User updateUserPassword(@PathVariable(name = "id") Integer id, @RequestBody @Valid UpdateUserPasswordForm form) {

        return userService.updatePassword(id, form);
    }

    @PostMapping("/ad/grant/{id}")
    public ResponseEntity<String> grantRoles(@PathVariable(name = "id") Integer userId, @RequestParam List<Role.RoleName> roleNames) {
        userService.grantRoles(userId, roleNames);
        return ResponseEntity.ok("Roles granted successfully.");
    }

    @DeleteMapping("/ad/revoke/{id}")
    public ResponseEntity<String> revokeRoles(@PathVariable(name = "id") Integer userId) {
        userService.revokeRoles(userId);
        return ResponseEntity.ok("Roles revoked successfully.");
    }

    private UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullname(user.getFullname());
        userDTO.setCreated_at(user.getCreated_at());

        return userDTO;
    }
}
