package com.example.user_practice_1.restcontroller;

import com.example.user_practice_1.dto.UserDto;
import com.example.user_practice_1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{userId}")
    UserDto getUserById(@PathVariable int userId) {
        return userService.findById(userId);
    }

    @PostMapping("/users")
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.save(userDto);
        return ResponseEntity.status(201).body(createdUser);
    }

    @PatchMapping("/users/{userId}")
    UserDto updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
        return userService.update(userId, userDto);
    }

    @PutMapping("/users/{userId}")
    UserDto updatePartialUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
        return userService.partialUpdate(userId, userDto);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUserById(@PathVariable int userId) {
        userService.deleteById(userId);
        return "Student with id " + userId + " was deleted successfully";
    }
}