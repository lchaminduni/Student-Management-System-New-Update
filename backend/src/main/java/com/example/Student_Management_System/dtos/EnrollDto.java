package com.example.Student_Management_System.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollDto {
    private Long enrollmentId;
    private String studentId;
    private String classId;
    private LocalDate enrollDate;
}
