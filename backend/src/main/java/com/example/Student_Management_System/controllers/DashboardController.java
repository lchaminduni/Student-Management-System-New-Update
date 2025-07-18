package com.example.Student_Management_System.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Student_Management_System.entities.Student;
import com.example.Student_Management_System.repositories.ClassRepository;
import com.example.Student_Management_System.repositories.EnrollRepository;
import com.example.Student_Management_System.repositories.PaymentRepository;
import com.example.Student_Management_System.repositories.StudentRepository;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ClassRepository classRepo;

    @Autowired
    private EnrollRepository enrollRepo;

    @Autowired
    private PaymentRepository paymentRepo;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getSummary() {
        Map<String, Long> summary = new HashMap<>();
        summary.put("students", studentRepo.count());
        summary.put("classes", classRepo.count());
        summary.put("enrollments", enrollRepo.count());
        summary.put("payments", paymentRepo.count());
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/registrations")
    public ResponseEntity<Map<String, Long>> getMonthlyRegistrations() {
    List<Student> students = studentRepo.findAll();
    Map<String, Long> monthCounts = new TreeMap<>(); // Sorted by month

    for (Student student : students) {
        if (student.getRegisteredDate() != null) {
            String month = student.getRegisteredDate().withDayOfMonth(1).toString().substring(0, 7);
            monthCounts.put(month, monthCounts.getOrDefault(month, 0L) + 1);
        }
    }

    return ResponseEntity.ok(monthCounts);
}

}
