package com.mamadou.campusgallery.controller;

import com.mamadou.campusgallery.dto.LoginRequest;
import com.mamadou.campusgallery.dto.RegisterRequest;
import com.mamadou.campusgallery.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(
            @RequestPart("user") RegisterRequest registerRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        try {
            return authService.register(registerRequest, profileImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest loginRequest) {
        try {
            return authService.login(loginRequest);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?>getLoggedInUser() {
        try{
            return authService.getLoggedUser();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not load User: " + e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?>logout(HttpServletRequest request, HttpServletResponse response) {
        try{
            return authService.logout(request,response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Logout failed: " + e.getMessage());
        }
    }

}
