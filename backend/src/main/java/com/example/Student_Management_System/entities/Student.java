package com.example.Student_Management_System.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String name;
    private String address;
    private LocalDate birthDate;
    private String fmName;
    private String contactNumber;
    private String gender;
    @Column(name = "registered_date")
    private LocalDate registeredDate;
}
