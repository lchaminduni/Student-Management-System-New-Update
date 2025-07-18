package com.example.Student_Management_System.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Student_Management_System.entities.Admin;
import com.example.Student_Management_System.repositories.AdminRepository;

@Service
public class AdminDetailsService implements UserDetailsService{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Admin admin = adminRepository.findByUsername(username).orElse(null);

        if (admin==null) {
            throw new UsernameNotFoundException("User is not found");
        }else{
            return org.springframework.security.core.userdetails.User.builder()
            .username(admin.getUsername())
            .password(admin.getPassword())
            .build();   
        }
    }
}
