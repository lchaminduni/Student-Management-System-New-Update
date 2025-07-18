package com.example.Student_Management_System.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentDto {
    private Long studentId;
    private String name;
    private String address;
    private LocalDate birthDate;
    private String fmName;
    private String contactNumber;
    private String gender;
    private LocalDate registeredDate;
}
