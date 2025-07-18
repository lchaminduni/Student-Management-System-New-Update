package com.example.Student_Management_System.services;

import java.util.ArrayList;

import com.example.Student_Management_System.dtos.StudentDto;
import com.example.Student_Management_System.entities.Student;

public interface StudentService {
    Student studentRegister (StudentDto studentDto);
    Student studentUpdate (StudentDto studentDto);
    Student studentDelete (Long studentId);
    StudentDto getStudents(Long studentId);
    ArrayList<StudentDto> getAllStudent();
    boolean studentExists (Long studentId);
}
