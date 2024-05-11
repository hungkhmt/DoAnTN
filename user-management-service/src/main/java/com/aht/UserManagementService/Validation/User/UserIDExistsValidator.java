package com.aht.UserManagementService.Validation.User;

import com.aht.UserManagementService.service.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class UserIDExistsValidator implements ConstraintValidator<UserIDExists, Integer> {
    @Autowired
    IUserService userService;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {

        if (StringUtils.isEmpty(id)) {
            return true;
        }

        return userService.isUserExistsByID(id);
    }
}
