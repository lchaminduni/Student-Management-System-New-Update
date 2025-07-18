package com.example.Student_Management_System.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Student_Management_System.entities.Admin;
import com.example.Student_Management_System.repositories.AdminRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
                .orElse(null);
        
        if (admin == null) {
            throw new UsernameNotFoundException("User is not found");
        }

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                .authorities(new ArrayList<>()) // empty roles for now
                .build();
    }
}
