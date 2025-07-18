package com.example.Student_Management_System.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Student_Management_System.dtos.StudentDto;
import com.example.Student_Management_System.entities.Student;
import com.example.Student_Management_System.services.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    
    @Autowired
    private StudentService studentService;

    //Register student
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@RequestBody StudentDto studentDto){
        Student registered=studentService.studentRegister(studentDto);
        return ResponseEntity.ok("Student registered with ID: " + registered.getStudentId());
    }

    //Update student
    @PutMapping("/update")
    public ResponseEntity<?> updateStudent(@RequestBody StudentDto studentDto) {
        Student updated=studentService.studentUpdate(studentDto);
        return ResponseEntity.ok("Student updated successfully");
    }

    //Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        Student deleted=studentService.studentDelete(id);
        return ResponseEntity.ok("Student deleted: "+deleted.getName());
    }

    //Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id){
        StudentDto studentDto = studentService.getStudents(id);
        return ResponseEntity.ok(studentDto);
    }

    //Get all students
    @GetMapping
    public ResponseEntity<ArrayList<StudentDto>> getAllStudents(){
        ArrayList<StudentDto> students=studentService.getAllStudent();
        return ResponseEntity.ok(students);
    }        
    
    
}
