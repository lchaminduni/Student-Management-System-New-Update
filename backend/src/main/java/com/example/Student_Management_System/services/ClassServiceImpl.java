package com.example.Student_Management_System.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Student_Management_System.dtos.ClassDto;
import com.example.Student_Management_System.entities.ClassEntity;
import com.example.Student_Management_System.repositories.ClassRepository;

@Service   
public class ClassServiceImpl implements ClassService{

    @Autowired
    private ClassRepository classRepository;

    @Override
    public String addClass(ClassDto classDto) {
        ClassEntity entity=mapToEntity(classDto);
        classRepository.save(entity);
        return "Success";
    }
        
    private ClassEntity mapToEntity(ClassDto dto) {
        ClassEntity entity = new ClassEntity();
        entity.setClassId(dto.getClassId());
        entity.setGrade(dto.getGrade());
        entity.setSubject(dto.getSubject());
        entity.setTeacherName(dto.getTeacherName());
        entity.setClassFee(dto.getClassFee());
        entity.setDate(dto.getDate());
        entity.setTime(dto.getTime());
        return entity;
    }
        
    @Override
    public String updateClass(ClassDto classDto) {
        if (classDto.getClassId() == null || !classRepository.existsById(classDto.getClassId())) {
            return "Class not found";
        }
        ClassEntity entity = mapToEntity(classDto);
        classRepository.save(entity);
        return "Success";
    }

    @Override
    public String deleteClass(Long id) {
        if (!classRepository.existsById(id)) {
            return "Class not found";
        }
        classRepository.deleteById(id);
        return "Success";
    }

    @Override
    public ClassDto getClassById(Long id) {
        ClassEntity entity = classRepository.findById(id).orElse(null);
        if (entity == null) return null;
        return mapToDto(entity);
    }

    @Override
    public List<ClassDto> getAllClasses() {
        List<ClassEntity> entities = classRepository.findAll();
        return entities.stream().map(this::mapToDto).toList();
    }
    
    private ClassDto mapToDto(ClassEntity entity){
        return new ClassDto(
                entity.getClassId(),
                entity.getGrade(),
                entity.getSubject(),
                entity.getTeacherName(),
                entity.getClassFee(),
                entity.getDate(),
                entity.getTime()
        );              
    }
}
            