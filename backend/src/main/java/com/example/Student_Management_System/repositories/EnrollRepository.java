package com.example.Student_Management_System.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Student_Management_System.entities.EnrollEntity;
import java.util.List;

public interface EnrollRepository extends JpaRepository<EnrollEntity, Long>{
    boolean existsByStudentId(String studentId);
    boolean existsByClassId(String classId);

    List<EnrollEntity> findByStudentId(String studentId);
    List<EnrollEntity> findByClassId(String classId);
    boolean existsByStudentIdAndClassId(String studentId, String classId);
}
