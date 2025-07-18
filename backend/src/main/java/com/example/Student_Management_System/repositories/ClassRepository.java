package com.example.Student_Management_System.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Student_Management_System.entities.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity, Long>{
    
}
