package com.example.Student_Management_System.services;

import java.util.List;

import com.example.Student_Management_System.dtos.ClassDto;

public interface ClassService {
    String addClass(ClassDto classDto);
    String updateClass(ClassDto classDto);
    String deleteClass(Long id);
    ClassDto getClassById(Long id);
    List<ClassDto>getAllClasses();    
}
