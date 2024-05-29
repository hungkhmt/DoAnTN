package com.aht.UserManagementService.service;

import com.aht.UserManagementService.dto.UserDTO;
import com.aht.UserManagementService.entity.Role;
import com.aht.UserManagementService.entity.User;
import com.aht.UserManagementService.entity.UserStatus;
import com.aht.UserManagementService.exception.AppException;
import com.aht.UserManagementService.exception.ErrorCode;
import com.aht.UserManagementService.form.user.*;
import com.aht.UserManagementService.repository.IRoleRepository;
import com.aht.UserManagementService.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
@Slf4j
public class UserService implements IUserService{
    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public void createUserFromAdmin(CreateUserForAdminForm form) {
        Set<Role> roles =  form.getRoles();
        Set<Role> rolesToAdd = new HashSet<>();

        if(roles != null) {
            for (Role role : roles) {
                //Kiểm tra role đã tồn tại hay chưa
                Role existingRole = roleRepository.findByRoleName(role.getRoleName());
                if (existingRole != null) {
                    rolesToAdd.add(existingRole);
                } else {
                    rolesToAdd.add(role);
                }
            }
        } else {
            Role role = roleRepository.findByRoleName(Role.RoleName.USER);
            rolesToAdd.add(role);
        }


        form.setRoles(rolesToAdd);

        User user = userFormToUserAd(form);

        user.setCreated_at(new Date());
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        userRepository.save(user);
    }

    public void createUser(CreateUserForm form) {
        Set<Role> rolesToAdd = new HashSet<>();
        Role role = roleRepository.findByRoleName(Role.RoleName.USER);
        if(role != null) {
            rolesToAdd.add(role);
        }


        User user = userFormToUser(form);
        user.setCreated_at(new Date());
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(rolesToAdd);
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUserActive() {
        return userRepository.findAllByStatus(UserStatus.ACTIVE);
    }

    public List<User> getAllUserDisable() {
        return userRepository.findAllByStatus(UserStatus.INACTIVE);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(UpdateUserForm newUser) {
        boolean userExists = isUserExistsByID(newUser.getId());
        Integer id = newUser.getId();
        if (id != null) {
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser != null) {
                // Chỉ cập nhật thông tin user, không cập nhật thông tin role
                existingUser.setUsername(newUser.getUsername());
                existingUser.setEmail(newUser.getEmail());
                existingUser.setFullname(newUser.getFullname());
                existingUser.setAddress(newUser.getAddress());
                existingUser.setDateOfBirth(newUser.getDateOfBirth());
                existingUser.setPhoneNumber(newUser.getPhoneNumber());
                return userRepository.save(existingUser);
            }
        }
        return null;
    }

    public User updateUserByAdmin(UpdateUserForAdminForm newUser) {
        boolean userExists = isUserExistsByID(newUser.getId());
        Integer id = newUser.getId();
        if (id != null) {
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser != null) {
                existingUser.setUsername(newUser.getUsername());
                existingUser.setEmail(newUser.getEmail());
                existingUser.setFullname(newUser.getFullname());
                existingUser.setPassword(newUser.getPassword());
                existingUser.setAddress(newUser.getAddress());
                existingUser.setDateOfBirth(newUser.getDateOfBirth());
                existingUser.setPhoneNumber(newUser.getPhoneNumber());
                existingUser.setRoles(newUser.getRoles());
                return userRepository.save(existingUser);
            }
        }
        return null;
    }

    @Transactional
    public void deleteUser(Integer userId) {
        // Xóa tất cả các liên kết giữa người dùng và vai trò của họ trong bảng user_role
        userRepository.findById(userId).ifPresent(user -> user.getRoles().clear());
        User user = userRepository.findById(userId).get();
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    @Transactional
    public User updatePassword(Integer id, UpdateUserPasswordForm form) {
        if (id == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (passwordEncoder.matches(form.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(form.getNewPassword()));
            return userRepository.save(user);
        } else {
            throw new AppException(ErrorCode.INCORRECT_PASSWORD);
        }
    }


    @Transactional
    public void revokeRoles(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getRoles().clear(); // Xóa tất cả các vai trò của người dùng
            userRepository.save(user);
        }
    }

    @Transactional
    public void grantRoles(Integer userId, List<Role.RoleName> roleNames) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Set<Role> rolesToAdd = new HashSet<>();
            for (Role.RoleName roleName : roleNames) {
                Role role = roleRepository.findByRoleName(roleName);
                if (role != null) {
                    rolesToAdd.add(role);
                }
            }
            user.getRoles().addAll(rolesToAdd);
            userRepository.save(user);
        }
    }

    public boolean isUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isUserExistsByID(Integer id) {
        return userRepository.existsById(id);
    }

    private User userFormToUserAd(CreateUserForAdminForm form) {
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        user.setFullname(form.getFullname());
        user.setAddress(form.getAddress());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setDateOfBirth(form.getDateOfBirth());
        user.setRoles(form.getRoles());

        return user;
    }

    private User userFormToUser(CreateUserForm form) {
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        user.setFullname(form.getFullname());
        user.setAddress(form.getAddress());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setDateOfBirth(form.getDateOfBirth());
        return user;
    }
}
