package com.example.Student_Management_System.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Student_Management_System.dtos.ClassDto;
import com.example.Student_Management_System.services.ClassService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/class")
@CrossOrigin(origins = "*")
public class ClassController {
    
    @Autowired
    private ClassService classService;

    @PostMapping("/add")
    public ResponseEntity<?> addClass(@RequestBody ClassDto classDto) {
        String result = classService.addClass(classDto);
        return ResponseEntity.ok(result);
    }

    // Update existing class
    @PutMapping("/update")
    public ResponseEntity<?> updateClass(@RequestBody ClassDto classDto) {
        String result = classService.updateClass(classDto);
        return ResponseEntity.ok(result);
    }

    // Delete class by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable Long id) {
        String result = classService.deleteClass(id);
        return ResponseEntity.ok(result);
    }

    // Get class by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClassDto> getClassById(@PathVariable Long id) {
        ClassDto classDto = classService.getClassById(id);
        return ResponseEntity.ok(classDto);
    }

    // Get all classes
    @GetMapping("/all")
    public ResponseEntity<List<ClassDto>> getAllClasses() {
        List<ClassDto> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }
    
}
