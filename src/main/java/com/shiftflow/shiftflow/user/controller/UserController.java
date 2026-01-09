package com.shiftflow.shiftflow.user.controller;

import com.shiftflow.shiftflow.user.dto.UserCreateRequest;
import com.shiftflow.shiftflow.user.dto.UserResponse;
import com.shiftflow.shiftflow.user.entity.User;
import com.shiftflow.shiftflow.user.service.UserService;
import jakarta.validation.Valid;
import jdk.jfr.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid UserCreateRequest request
    ) {
        User user = userService.createUser(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UserResponse(user));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(new UserResponse(user));
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>> listAll() {
        List<UserResponse> response = userService.listAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<UserResponse> deactivate(@PathVariable Long id) {
        User user = userService.deactivateUser(id);
        return ResponseEntity.ok(new UserResponse(user));
    }


}
