package com.example.Student_Management_System.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Student_Management_System.entities.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>{
    boolean existsByStudentIdAndClassIdAndMonth(String studentId,String classId,String month);
    List<PaymentEntity> findByStudentId(String studentId);

    @Query("SELECT p FROM PaymentEntity p WHERE FUNCTION('DATE_FORMAT', p.paymentDate, '%Y-%m-%d') = :date")
    List<PaymentEntity> findByPaymentDate(@Param("date") String date);

    @Query("SELECT p FROM PaymentEntity p WHERE FUNCTION('DATE_FORMAT', p.paymentDate, '%Y-%m') = :month")
    List<PaymentEntity> findByPaymentMonth(@Param("month") String month);

    @Query("SELECT p.studentId, SUM(p.totalPayment) FROM PaymentEntity p GROUP BY p.studentId")
    List<Object[]> getTotalPaymentByStudent();
}
