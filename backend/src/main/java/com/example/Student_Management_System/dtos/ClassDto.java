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
public class ClassDto {
    private Long classId;
    private String grade;
    private String subject;
    private String teacherName;
    private BigDecimal classFee;
    private Date date;
    private String time;
}
