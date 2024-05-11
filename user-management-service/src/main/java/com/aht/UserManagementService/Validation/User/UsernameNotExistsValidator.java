package com.aht.UserManagementService.Validation.User;

import com.aht.UserManagementService.service.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UsernameNotExistsValidator implements ConstraintValidator<UsernameNotExists, String> {
    @Autowired
    IUserService userService;

    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        return username != null && !userService.isUserExistsByUsername(username);
    }
}
