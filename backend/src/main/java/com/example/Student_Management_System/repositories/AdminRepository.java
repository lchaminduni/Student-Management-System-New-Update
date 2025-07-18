package com.example.Student_Management_System.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Student_Management_System.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    Optional<Admin> findByUsername(String username);
}
