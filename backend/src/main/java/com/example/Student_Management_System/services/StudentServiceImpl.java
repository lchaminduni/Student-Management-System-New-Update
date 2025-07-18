package com.example.Student_Management_System.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Student_Management_System.dtos.StudentDto;
import com.example.Student_Management_System.entities.Student;
import com.example.Student_Management_System.repositories.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student studentRegister(StudentDto studentDto) {
        Student student = dtoToEntity(studentDto);
        return studentRepository.save(student);
    }
        
    private Student dtoToEntity(StudentDto studentDto) {
        Student student=new Student();
        student.setStudentId(studentDto.getStudentId());
        student.setName(studentDto.getName());
        student.setAddress(studentDto.getAddress());
        student.setBirthDate(studentDto.getBirthDate());
        student.setFmName(studentDto.getFmName());
        student.setContactNumber(studentDto.getContactNumber());
        student.setGender(studentDto.getGender());
        student.setRegisteredDate(studentDto.getRegisteredDate());
        return student;

    }
        
    @Override
    public Student studentUpdate(StudentDto studentDto) {
        if (!studentRepository.existsById(studentDto.getStudentId())) {
            throw new RuntimeException("Student not found for updata");
        }

        Student student=dtoToEntity(studentDto);
        return studentRepository.save(student);
    }

    @Override
    public Student studentDelete(Long studentId) {
        Student student=studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found for deletion"));
        studentRepository.delete(student);
        return student;
    }

    @Override
    public StudentDto getStudents(Long studentId) {
        Student student=studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        //studentRepository.delete(student);
        return entityToDto(student);
    }
        
            private StudentDto entityToDto(Student student) {
                return new StudentDto(student.getStudentId(), student.getName(), student.getAddress(), student.getBirthDate(), student.getFmName(), student.getContactNumber(), student.getGender(),student.getRegisteredDate());
            }
        
    @Override
    public ArrayList<StudentDto> getAllStudent() {
        List<Student> students=studentRepository.findAll();
        ArrayList<StudentDto> dtos=new ArrayList<>();
        for(Student student : students){
            dtos.add(entityToDto(student));
        }

        return dtos;
    }

    @Override
    public boolean studentExists(Long studentId) {
        return studentRepository.existsById(studentId);
    }   
} 
