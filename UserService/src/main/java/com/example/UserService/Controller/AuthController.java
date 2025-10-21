package com.example.UserService.Controller;

import com.example.UserService.Dto.LoginRequest;
import com.example.UserService.Dto.LoginResponse;
import com.example.UserService.Dto.RegisterRequest;
import com.example.UserService.Service.UserService;
import com.example.UserService.Utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);

        if (response.getToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Kayıt başarılı 🎉");
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        if (jwtUtils.validateToken(token)) {

            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("userId", jwtUtils.getUserIdFromToken(token));
            response.put("username", jwtUtils.getUsernameFromToken(token));
            response.put("role", jwtUtils.getRoleFromToken(token));

            return ResponseEntity.ok(response);
        } else {

            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            response.put("message", "Token geçersiz veya süresi dolmuş");

            return ResponseEntity.status(401).body(response);
        }
    }
}

