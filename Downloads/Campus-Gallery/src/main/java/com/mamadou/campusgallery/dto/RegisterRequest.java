package com.mamadou.campusgallery.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    @NotBlank(message = "Name cannot be blank")
    private String fullName;

    @Email(message = "email must be valid")
    private String email;

    @Size(min = 6,message = "Password should not be less than 6 characters")
    private String password;
}
