package com.example.Student_Management_System.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Student_Management_System.dtos.EnrollDto;
import com.example.Student_Management_System.services.EnrollService;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollController {
    @Autowired
    private EnrollService enrollService;

    @PostMapping("/add")
    public ResponseEntity<String> enrollStudent(@RequestBody EnrollDto enrollDto) {
        String result = enrollService.enroll(enrollDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEnrollment(@RequestBody EnrollDto enrollDto) {
        String result = enrollService.update(enrollDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEnrollment(@PathVariable Long id) {
        boolean deleted = enrollService.delete(id);
        return ResponseEntity.ok(deleted ? "Deleted successfully" : "Enrollment not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollDto> getEnrollment(@PathVariable Long id) {
        EnrollDto dto = enrollService.get(id);
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<EnrollDto>> getAllEnrollments() {
        return ResponseEntity.ok(enrollService.getAll());
    }

    @GetMapping("/student-exists/{studentId}")
    public ResponseEntity<Boolean> checkStudentExists(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollService.isStudentExists(studentId));
    }

    @GetMapping("/class-exists/{classId}")
    public ResponseEntity<Boolean> checkClassExists(@PathVariable Long classId) {
        return ResponseEntity.ok(enrollService.isClassExists(classId));
    }

    @GetMapping("/student-details/{studentId}")
    public ResponseEntity<?> getStudentDetails(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollService.getStudentDetails(studentId));
    }

    @GetMapping("/class-details/{classId}")
    public ResponseEntity<?> getClassDetails(@PathVariable Long classId) {
        return ResponseEntity.ok(enrollService.getClassDetails(classId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getEnrollmentsByStudent(@PathVariable String studentId) {
        Map<String, Object> response = enrollService.getEnrollmentsByStudentId(studentId);
        return ResponseEntity.ok(response);
    }

}
