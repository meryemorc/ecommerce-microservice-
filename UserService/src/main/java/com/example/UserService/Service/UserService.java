package com.example.UserService.Service;

import com.example.UserService.Dto.LoginRequest;
import com.example.UserService.Dto.LoginResponse;
import com.example.UserService.Dto.RegisterRequest;
import com.example.UserService.Dto.UserDTO;
import com.example.UserService.Entity.User;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.Utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.UserService.Config.PasswordEncoderConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    public UserDTO findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new RuntimeException("kullanıcı bulunamadı");
        }
        UserDTO userDto = new UserDTO();
        User getUser = user.get();
        userDto.setUsername(getUser.getUsername());
        userDto.setPassword(getUser.getPassword());
        userDto.setRole(getUser.getRole());
        userDto.setEmail(getUser.getEmail());
        return userDto;
    }

    public List<UserDTO> findAllUser() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RuntimeException("kullanıcı yok");
        }
        List<UserDTO> dtoList = new ArrayList<>();
        for (User user : users) {
            UserDTO dto = new UserDTO();
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("kullanıcı bulunamadı"));
    }

    public void createUser(User user) {

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        userRepository.save(user);
    }

    public void updateUser(Long id, UserDTO userDto) {
        User mevcutuser = userRepository.findById(id).orElse(null);
        if (mevcutuser == null) {
            throw new RuntimeException("kullanıcı bulunamadı");
        }
        mevcutuser.setUsername(userDto.getUsername());
        mevcutuser.setEmail(userDto.getEmail());
        mevcutuser.setPassword(userDto.getPassword());
        userRepository.save(mevcutuser);
    }

    public void deleteUser(Long id) {
        User mevcutuser = userRepository.findById(id).orElse(null);
        if (null == mevcutuser) {
            throw new RuntimeException("kullanıcı bulunamadı");
        }
        userRepository.delete(mevcutuser);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        if (user == null) {
            throw new RuntimeException("Kullanıcı bulunamadı");
        }

        // Password kontrolü (BCrypt) ← DEĞİŞTİ!
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Şifre hatalı");
        }

        // JWT Token Generate ← YENİ!
        String token = jwtUtils.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );

        // Token ile döndür ← DEĞİŞTİ!
        return new LoginResponse(
                "Giriş başarılı",
                user.getUsername(),
                user.getRole(),
                token  // ← YENİ!
        );
    }



    public void register(RegisterRequest request) {
        User varMi = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (varMi != null) {
            throw new RuntimeException("bu kullanici adi kullaniliyor");
        }

        User yeniuser = new User();
        yeniuser.setUsername(request.getUsername());

        // Password'ü HASH'LE! ← DEĞİŞTİ!
        yeniuser.setPassword(passwordEncoder.encode(request.getPassword()));

        yeniuser.setEmail(request.getEmail());
        yeniuser.setRole("USER");
        userRepository.save(yeniuser);
    }

}