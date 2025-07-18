package com.example.Student_Management_System.services;

import com.example.Student_Management_System.entities.Admin;

public interface AdminService {
    Admin register(Admin admin);
    Admin login(String username, String password);    
}
