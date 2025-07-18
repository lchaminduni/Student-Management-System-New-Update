package com.example.Student_Management_System.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Student_Management_System.entities.Admin;
import com.example.Student_Management_System.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin register(Admin admin) {
        admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public Admin login(String username, String password) {
        Admin admin=adminRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Userneme not found"));

        if (!new BCryptPasswordEncoder().matches(password, admin.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return admin;
    }
    
     
}
