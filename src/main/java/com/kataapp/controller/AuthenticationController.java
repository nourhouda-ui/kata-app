package com.kataapp.controller;

import com.kataapp.dao.User;
import com.kataapp.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    public ResponseEntity<String> authenticate(@RequestParam String email, @RequestParam String password) {
        String token = authenticationService.authenticate(email, password);
        return token != null ? ResponseEntity.ok(token) : ResponseEntity.status(401).body("Unauthorized");
    }

    @PostMapping("/account")
    public ResponseEntity<String> createAccount(@RequestBody User user) {
        return ResponseEntity.status(201).body("Account created successfully");
    }
}
