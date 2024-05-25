package com.aht.UserManagementService.config;

import com.aht.UserManagementService.entity.Role;
import com.aht.UserManagementService.entity.User;
import com.aht.UserManagementService.entity.UserStatus;
import com.aht.UserManagementService.repository.IRoleRepository;
import com.aht.UserManagementService.repository.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppInitConfig {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(IUserRepository userRepository, IRoleRepository roleRepository) {
        return args -> {
            if(userRepository.findByUsername(ADMIN_USER_NAME) == null) {
                Set<Role> rolesToAdd = new HashSet<>();
                Role adminRole = roleRepository.findByRoleName(Role.RoleName.ADMIN);
                Role userRole = roleRepository.findByRoleName(Role.RoleName.USER);
                if(adminRole == null) {
                    adminRole = new Role(1, Role.RoleName.ADMIN);
                    roleRepository.save(adminRole);
                }
                if(userRole == null) {
                    userRole = new Role(2, Role.RoleName.USER);
                    roleRepository.save(userRole);
                }

                rolesToAdd.add(adminRole);

                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .status(UserStatus.ACTIVE)
                        .address("null")
                        .fullname("admin")
                        .created_at(new Date())
                        .dateOfBirth("null")
                        .email("null")
                        .phoneNumber("null")
                        .roles(rolesToAdd)
                        .status(UserStatus.ACTIVE)
                        .build();

                userRepository.save(user);
            }
            log.info("Application initialization completed .....");
        };
    }
}
