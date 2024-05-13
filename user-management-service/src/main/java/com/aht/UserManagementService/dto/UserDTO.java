package com.aht.UserManagementService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String fullname;
    private String dateOfBirth;
    private String phoneNumber;
    private String address;
    private Date created_at;
}
