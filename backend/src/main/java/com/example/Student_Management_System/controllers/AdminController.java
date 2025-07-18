package com.example.Student_Management_System.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Student_Management_System.dtos.LoginRequest;
import com.example.Student_Management_System.entities.Admin;
import com.example.Student_Management_System.services.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.Student_Management_System.security.JwtUtils;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Admin admin){
        adminService.register(admin); // your service method

    // Return custom success message
    Map<String, String> response = new HashMap<>();
    response.put("message", "Admin registered successfully");

    return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAdmin(@RequestBody LoginRequest loginRequest) {

        try {
            // authenticate using Spring Security
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            return ResponseEntity.ok(new HashMap<>() {{
                put("token", jwt);
                put("username", loginRequest.getUsername());
            }});

        } catch (BadCredentialsException ex) {
            // Custom error message if login fails
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new HashMap<>() {{
                    put("error", "Invalid username or password");
                }}
            );
        }
    }
}
