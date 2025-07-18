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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Student_Management_System.dtos.ClassDto;
import com.example.Student_Management_System.dtos.PaymentDto;
import com.example.Student_Management_System.dtos.StudentDto;
import com.example.Student_Management_System.services.PaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @PostMapping("/add")
    public ResponseEntity<String> savePayment(@RequestBody PaymentDto dto) {
        return ResponseEntity.ok(paymentService.savePayment(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDto>> getAll() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updatePayment(@RequestBody PaymentDto dto) {
        return ResponseEntity.ok(paymentService.updatePayment(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.deletePayment(id));
    }

    @GetMapping("/status/{studentId}")
    public ResponseEntity<?> getStudentPaymentStatus(@PathVariable String studentId) {
        return ResponseEntity.ok(paymentService.getStudentPaymentStatus(studentId));
    }

    @GetMapping("/report/daily")
    public ResponseEntity<List<PaymentDto>> getDailyReport(@RequestParam String date) {
        return ResponseEntity.ok(paymentService.getDailyReport(date));
    }

    @GetMapping("/report/monthly")
    public ResponseEntity<List<PaymentDto>> getMonthlyReport(@RequestParam String month) {
        return ResponseEntity.ok(paymentService.getMonthlyReport(month));
    }

    @GetMapping("/report/total-by-student")
    public ResponseEntity<List<Map<String, Object>>> getTotalPaymentByStudent() {
        return ResponseEntity.ok(paymentService.getTotalPaymentByStudent());
    }


}
