package com.example.UserService.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "username bos olamaz")
    private String username;

    @NotBlank(message = "password bos olamaz")
    private String password;

    @NotBlank(message = "lütfewn bir rol giriniz")
    private String role;
}
