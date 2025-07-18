package com.example.Student_Management_System.dtos;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long paymentId;
    private String studentId;
    private String classId;
    private String month;
    private BigDecimal totalPayment;
    private Date paymentDate;
}
