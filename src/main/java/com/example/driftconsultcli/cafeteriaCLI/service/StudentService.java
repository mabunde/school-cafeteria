package com.example.driftconsultcli.cafeteriaCLI.service;

import com.example.driftconsultcli.cafeteriaCLI.models.Student;

import java.util.List;

public interface StudentService {

    public List<Student> getAllStudents();
    public boolean existsByUsername(String username);
    public Student addStudent(Student student);
    Student findById(Long id);
    public Student findByUsername(String username);


}
