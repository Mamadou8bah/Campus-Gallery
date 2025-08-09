package com.mamadou.campusgallery.service;

import com.mamadou.campusgallery.dto.LoginRequest;
import com.mamadou.campusgallery.dto.RegisterRequest;
import com.mamadou.campusgallery.mapper.UserMapper;
import com.mamadou.campusgallery.model.User;
import com.mamadou.campusgallery.repository.UserRepository;
import com.mamadou.campusgallery.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<?>register(RegisterRequest registerRequest, MultipartFile file)throws IOException {
        User user = userRepository.findByEmail(registerRequest.getEmail());
        if (user != null) {
            return new  ResponseEntity<>("User already exists", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        User newUser = new User();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setFullName(registerRequest.getFullName());
        String profileImageUrl=cloudinaryService.uploadFile(file);
        newUser.setProfileUrl(profileImageUrl);
        userRepository.save(newUser);
        return new ResponseEntity<>(UserMapper.userToRegisterResponse(newUser), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            return new  ResponseEntity<>("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new  ResponseEntity<>("Wrong Password", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok().body(jwtUtil.generateToken(user.getEmail()));
    }

    public ResponseEntity<?>getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return new  ResponseEntity<>("You are not authenticated", HttpStatus.UNAUTHORIZED);
        }
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new  ResponseEntity<>("User not found", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ResponseEntity.ok().body(UserMapper.userToRegisterResponse(user));
    }

    @Transactional
    public ResponseEntity<?>logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok().body("Logged out! Please Clear JWT at client side");
    }

}
