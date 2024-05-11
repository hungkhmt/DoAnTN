package com.aht.UserManagementService.form.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPasswordForm {
    private String oldPassword;

    @NotBlank(message = "{User.createUser.password.NotBlank}")
    @Length(min = 6, max = 30, message = "{User.createUser.password.LenghtRange}")
    private String newPassword;
}
