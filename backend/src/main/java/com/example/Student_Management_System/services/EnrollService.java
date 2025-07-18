package com.example.Student_Management_System.services;

import java.util.List;
import java.util.Map;

import com.example.Student_Management_System.dtos.ClassDto;
import com.example.Student_Management_System.dtos.EnrollDto;
import com.example.Student_Management_System.dtos.StudentDto;

public interface EnrollService {
    String enroll(EnrollDto enrollDto);
    String update(EnrollDto enrollDto);
    EnrollDto get(Long enrollmentId);
    List<EnrollDto>getAll();
    boolean delete(Long enrollmentId);
    boolean isStudentExists(Long studentId);
    boolean isClassExists(Long classId);
    ClassDto getClassDetails(Long classId);
    StudentDto getStudentDetails(Long studentId);
    Map<String, Object> getEnrollmentsByStudentId(String studentId);


}
