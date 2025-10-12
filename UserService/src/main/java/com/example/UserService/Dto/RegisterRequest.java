package com.example.UserService.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "username bos olamaz")
    private String username;

    @NotBlank(message = "password bos olamaz")
    private String password;

    @Email(message = "email formatına uymuyor")
    @NotBlank(message = "email bos olamaz")
    private String email;
}