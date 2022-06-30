package com.example.driftconsultcli.cafeteriaCLI.service;

import com.example.driftconsultcli.cafeteriaCLI.models.Student;
import com.example.driftconsultcli.cafeteriaCLI.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService{

    private  final StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public boolean existsByUsername(String username) {
        return studentRepository.existsByUsername(username);
    }

    @Override
    public Student addStudent(Student student) {
        return null;
    }

    @Override
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }


}
