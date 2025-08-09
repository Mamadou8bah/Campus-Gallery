package com.mamadou.campusgallery.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    @Email(message = "Email must be valid")
    private String email;
    @Size(min = 6,message = "Password too short")
    private String password;
}
