package com.aht.UserManagementService.Validation.User;

import com.aht.UserManagementService.service.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class EmailNotExistsValidator implements ConstraintValidator<EmailNotExists, String> {
    @Autowired
    IUserService userService;

    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isEmpty(email)) {
            return true;
        }

        return !userService.isUserExistsByEmail(email);
    }
}
