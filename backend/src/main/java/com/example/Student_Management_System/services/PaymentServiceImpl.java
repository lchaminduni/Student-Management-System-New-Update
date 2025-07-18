package com.example.Student_Management_System.services;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Student_Management_System.dtos.ClassDto;
import com.example.Student_Management_System.dtos.PaymentDto;
import com.example.Student_Management_System.dtos.StudentDto;
import com.example.Student_Management_System.entities.ClassEntity;
import com.example.Student_Management_System.entities.EnrollEntity;
import com.example.Student_Management_System.entities.PaymentEntity;
import com.example.Student_Management_System.entities.Student;
import com.example.Student_Management_System.repositories.ClassRepository;
import com.example.Student_Management_System.repositories.EnrollRepository;
import com.example.Student_Management_System.repositories.PaymentRepository;
import com.example.Student_Management_System.repositories.StudentRepository;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ClassRepository classRepo;

    @Autowired
    private EnrollRepository enrollRepo;

    @Override
    public String savePayment(PaymentDto dto) {
        if (!studentRepo.existsById(Long.valueOf(dto.getStudentId()))) {
            throw new RuntimeException("Student does not exist");
        }
        if (!classRepo.existsById(Long.valueOf(dto.getClassId()))) {
            throw new RuntimeException("Class does not exist");
        }
        if (paymentRepo.existsByStudentIdAndClassIdAndMonth(dto.getStudentId(), dto.getClassId(), dto.getMonth())) {
            throw new RuntimeException("Already paid");
        }

        PaymentEntity entity = mapToEntity(dto);
                paymentRepo.save(entity);
                return "Payment saved successfully.";
            }
        
            private PaymentEntity mapToEntity(PaymentDto dto) {
                return new PaymentEntity(dto.getPaymentId(), dto.getStudentId(), dto.getClassId(),
                dto.getMonth(), dto.getTotalPayment(), dto.getPaymentDate());
            }
        
    @Override
    public String updatePayment(PaymentDto dto) {
        PaymentEntity existing = paymentRepo.findById(dto.getPaymentId())
            .orElseThrow(() -> new RuntimeException("Payment not found"));

        PaymentEntity updated = mapToEntity(dto);
        paymentRepo.save(updated);
        return "Payment updated successfully.";
    }

    @Override
    public String deletePayment(Long id) {
        paymentRepo.deleteById(id);
        return "Payment deleted successfully.";
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private PaymentDto mapToDto(PaymentEntity entity) {
        return new PaymentDto(entity.getPaymentId(), entity.getStudentId(), entity.getClassId(),
                entity.getMonth(), entity.getTotalPayment(), entity.getPaymentDate());
    }

    @Override
    public PaymentDto getPayment(Long id) {
        PaymentEntity entity = paymentRepo.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        return mapToDto(entity);
    }

    @Override
    public List<Map<String, Object>> getStudentPaymentStatus(String studentId) {
        List<EnrollEntity> enrollments = enrollRepo.findByStudentId(studentId);
        List<Map<String, Object>> statusList = new ArrayList<>();

        for (EnrollEntity enroll : enrollments) {
            Map<String, Object> map = new HashMap<>();
            map.put("classId", enroll.getClassId());
            map.put("enrollDate", enroll.getEnrollDate());
            map.put("paid", paymentRepo.existsByStudentIdAndClassIdAndMonth(studentId, enroll.getClassId(), YearMonth.now().toString()));
            map.put("classFee", classRepo.findById(Long.valueOf(enroll.getClassId())).get().getClassFee());
            statusList.add(map);
        }

        return statusList;
    }

    @Override
    public List<PaymentDto> getDailyReport(String date) {
        return paymentRepo.findByPaymentDate(date).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getMonthlyReport(String month) {
        return paymentRepo.findByPaymentMonth(month).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getTotalPaymentByStudent() {
        List<Object[]> result = paymentRepo.getTotalPaymentByStudent();
        List<Map<String, Object>> mapped = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> map = new HashMap<>();
            map.put("studentId", row[0]);
            map.put("total", row[1]);
            mapped.add(map);
        }
        return mapped;
    }

    /*@Override
    public String savePayment(PaymentDto dto) {
        if (paymentRepo.existsByStudentIdAndMonth(dto.getStudentId(), dto.getMonth())) {
            throw new RuntimeException("Payment already exists for this student and month.");
        }

        PaymentEntity entity=mapToEntity(dto);
        paymentRepo.save(entity);
        return "Payment saved successfully.";
    }
        
            private PaymentEntity mapToEntity(PaymentDto dto) {
                return new PaymentEntity(dto.getPaymentId(),dto.getStudentId(),dto.getClassId()
                ,dto.getMonth(),dto.getTotalPayment(),dto.getPaymentDate());
            }
        
    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private PaymentDto mapToDto(PaymentEntity entity) {
        return new PaymentDto(entity.getPaymentId(),entity.getStudentId(),entity.getClassId()
        ,entity.getMonth(),entity.getTotalPayment(),entity.getPaymentDate());
    }

    @Override
    public PaymentDto getPayment(Long id) {
        PaymentEntity entity=paymentRepo.findById(id).orElseThrow(()-> new RuntimeException("Payment not found"));
        return mapToDto(entity);
    }

    @Override
    public boolean isDuplicatePayment(String studentId, String month) {
        return paymentRepo.existsByStudentIdAndMonth(studentId, month);
    }

    @Override
    public boolean isStudentExists(String studentId) {
        return studentRepo.existsById(Long.valueOf(studentId));
    }

    @Override
    public boolean isClassExists(String classId) {
        return classRepo.existsById(Long.valueOf(classId));
    }

    @Override
    public StudentDto findStudentById(String studentId) {
        Student student=studentRepo.findById(Long.valueOf(studentId)).orElseThrow(()-> new RuntimeException("Student not found"));
        return new StudentDto(student.getStudentId(), student.getName(), student.getAddress(), student.getBirthDate(), student.getFmName(), student.getContactNumber(), student.getGender());
    }

    @Override
    public ClassDto findClassById(String classId) {
        ClassEntity classEntity=classRepo.findById(Long.valueOf(classId)).orElseThrow(()-> new RuntimeException("Class not found"));
        return new ClassDto(classEntity.getClassId(),classEntity.getGrade(),classEntity.getSubject(),classEntity.getTeacherName(),classEntity.getClassFee(),classEntity.getDate(),classEntity.getTime());
    }*/
    
}
