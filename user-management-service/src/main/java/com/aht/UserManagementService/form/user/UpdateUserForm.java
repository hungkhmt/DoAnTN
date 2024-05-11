package com.aht.UserManagementService.form.user;

import com.aht.UserManagementService.Validation.User.EmailNotExists;
import com.aht.UserManagementService.Validation.User.UserIDExists;
import com.aht.UserManagementService.Validation.User.UsernameNotExists;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserForm {
    @NotNull(message = "{User.updateUser.id.NotNull}")
    private Integer id;

    @NotBlank(message = "{User.createUser.username.NotBlank}")
    @Length(min = 6, max = 30, message = "{User.createUser.username.LenghtRange}")
    @UsernameNotExists(message = "{User.createUser.username.NotExists}")
    private String username;

    @NotBlank(message = "{User.createUser.email.NotBlank}")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{User.email.Pattern}")
    @EmailNotExists(message = "{User.createUser.email.NotExists}")
    private String email;

    @NotBlank(message = "{User.createUser.fullname.NotBlank}")
    @Length(min = 6, max = 50, message = "{User.createUser.fullname.LenghtRange}")
    private String fullname;
}
