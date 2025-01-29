package com.example.user_practice_1.restcontroller;

import com.example.user_practice_1.dto.UserDto;
import com.example.user_practice_1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }
        UserDto createdUser = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PatchMapping("/users/{userId}")
    ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult, @PathVariable int userId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }

        userService.update(userId, userDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{userId}")
    ResponseEntity<?> updatePartialUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult, @PathVariable int userId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }
        userService.partialUpdate(userId, userDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable int userId) {
        boolean isDeleted = userService.deleteById(userId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Map<String, String> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }
}