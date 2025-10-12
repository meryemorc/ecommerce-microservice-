package com.example.UserService.Controller;

import com.example.UserService.Dto.LoginRequest;
import com.example.UserService.Dto.LoginResponse;
import com.example.UserService.Dto.RegisterRequest;
import com.example.UserService.Dto.UserDTO;
import com.example.UserService.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse loginMi = userService.login(request);
        return ResponseEntity.ok(loginMi);

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        userService.register(request);
        return ResponseEntity.ok("kayıt başarılı");
    }
}
