package com.example.Student_Management_System.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Student_Management_System.dtos.ClassDto;
import com.example.Student_Management_System.dtos.EnrollDto;
import com.example.Student_Management_System.dtos.StudentDto;
import com.example.Student_Management_System.entities.ClassEntity;
import com.example.Student_Management_System.entities.EnrollEntity;
import com.example.Student_Management_System.repositories.ClassRepository;
import com.example.Student_Management_System.repositories.EnrollRepository;
import com.example.Student_Management_System.repositories.StudentRepository;

@Service
public class EnrollServiceImpl implements EnrollService{

    @Autowired
    private EnrollRepository enrollRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Override
    public String enroll(EnrollDto enrollDto) {
        EnrollEntity enrollEntity=mapToEntity(enrollDto);
        enrollRepository.save(enrollEntity);
        return "Success";    
    }
        
    private EnrollEntity mapToEntity(EnrollDto enrollDto) {
        EnrollEntity entity=new EnrollEntity();
        entity.setEnrollmentId(enrollDto.getEnrollmentId());
        entity.setStudentId(enrollDto.getStudentId());
        entity.setClassId(enrollDto.getClassId());
        entity.setEnrollDate(enrollDto.getEnrollDate());
        return entity;
    }
        
    @Override
    public String update(EnrollDto enrollDto) {
        if (enrollDto.getEnrollmentId()==null || !enrollRepository.existsById(enrollDto.getEnrollmentId())) {
            return "Enrollment not found";
        }

        EnrollEntity enrollEntity=mapToEntity(enrollDto);
        enrollRepository.save(enrollEntity);
        return "Success";
    }

    @Override
    public EnrollDto get(Long enrollmentId) {
        return enrollRepository.findById(enrollmentId).map(this::mapToDto).orElse(null);
    }

    private EnrollDto mapToDto(EnrollEntity entity) {
        return new EnrollDto(
                entity.getEnrollmentId(),
                entity.getStudentId(),
                entity.getClassId(),
                entity.getEnrollDate()
        );
    }

    @Override
    public List<EnrollDto> getAll() {
         return enrollRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long enrollmentId) {
        if (!enrollRepository.existsById(enrollmentId)) return false;
        enrollRepository.deleteById(enrollmentId);
        return true;
    }

    @Override
    public boolean isStudentExists(Long studentId) {
        return studentRepository.existsById(studentId);
    }

    @Override
    public boolean isClassExists(Long classId) {
        return classRepository.existsById(classId);
    }

    @Override
    public ClassDto getClassDetails(Long classId) {
        return classRepository.findById(classId)
                .map(entity -> new ClassDto(
                        entity.getClassId(),
                        entity.getGrade(),
                        entity.getSubject(),
                        entity.getTeacherName(),
                        entity.getClassFee(),
                        entity.getDate(),
                        entity.getTime()
                ))
                .orElse(null);
    }

    @Override
    public StudentDto getStudentDetails(Long studentId) {
        return studentRepository.findById(studentId)
                .map(entity -> new StudentDto(
                        entity.getStudentId(),
                        entity.getName(),
                        entity.getAddress(),
                        entity.getBirthDate(),
                        entity.getFmName(),
                        entity.getContactNumber(),
                        entity.getGender(),
                        entity.getRegisteredDate()
                ))
                .orElse(null);
    }

    @Override
    public Map<String, Object> getEnrollmentsByStudentId(String studentId) {
        List<EnrollEntity> enrollments = enrollRepository.findByStudentId(studentId);

    List<Map<String, Object>> classList = new ArrayList<>();
    double totalFee = 0;

    for (EnrollEntity enroll : enrollments) {
        try {
            Long classIdLong = Long.parseLong(enroll.getClassId());

            Optional<ClassEntity> classEntityOpt = classRepository.findById(classIdLong);
            if (classEntityOpt.isPresent()) {
                ClassEntity cls = classEntityOpt.get();

                Map<String, Object> classDetails = new HashMap<>();
                classDetails.put("classId", cls.getClassId());
                classDetails.put("grade", cls.getGrade());
                classDetails.put("subject", cls.getSubject());
                classDetails.put("teacherName", cls.getTeacherName());
                classDetails.put("classFee", cls.getClassFee());
                classDetails.put("date", cls.getDate());
                classDetails.put("time", cls.getTime());
                classDetails.put("enrollDate", enroll.getEnrollDate());

                totalFee += cls.getClassFee().doubleValue();

                classList.add(classDetails);
            }
        } catch (NumberFormatException e) {
            // You can log this if needed
            continue; // Skip invalid classId entries
        }
    }

    Map<String, Object> result = new HashMap<>();
    result.put("enrolledClasses", classList);
    result.put("totalFee", totalFee);

    return result;
    } 
}
