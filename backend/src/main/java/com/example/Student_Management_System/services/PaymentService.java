package com.example.Student_Management_System.services;

import java.util.List;
import java.util.Map;

import com.example.Student_Management_System.dtos.ClassDto;
import com.example.Student_Management_System.dtos.PaymentDto;
import com.example.Student_Management_System.dtos.StudentDto;

public interface PaymentService {
    String savePayment(PaymentDto dto);
    String updatePayment(PaymentDto dto);
    String deletePayment(Long id);
    List<PaymentDto> getAllPayments();
    PaymentDto getPayment(Long id);
    List<Map<String,Object>> getStudentPaymentStatus(String studentId);
    List<PaymentDto> getDailyReport(String date);
    List<PaymentDto> getMonthlyReport(String month);
    List<Map<String, Object>> getTotalPaymentByStudent();

    //boolean isDuplicatePayment(String studentId, String month);
    //boolean isStudentExists(String studentId);
    //boolean isClassExists(String classId);
    //StudentDto findStudentById(String studentId);
    //ClassDto findClassById(String classId);
}
