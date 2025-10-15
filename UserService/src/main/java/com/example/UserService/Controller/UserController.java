package com.example.UserService.Controller;

import com.example.UserService.Dto.UserDTO;
import com.example.UserService.Entity.User;
import com.example.UserService.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        List<UserDTO> userDto = userService.findAllUser();
        return ResponseEntity.ok(userDto);
    }
    @GetMapping("/id/{id}")
    public UserDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @GetMapping("/name")
    public UserDTO getUserByUsername(@RequestParam String username){
        return    userService.findByUsername(username);
    }
    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDto){
        userService.updateUser(id, userDto);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id)
    {

        userService.deleteUser(id);
    }
}
