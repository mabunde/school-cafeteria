package com.example.driftconsultcli.cafeteriaCLI.service;

import com.example.driftconsultcli.cafeteriaCLI.models.Student;
import com.example.driftconsultcli.cafeteriaCLI.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@Service
public class StudentServiceImpl extends Observable  implements StudentService{
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private Observer observer;
    private List<Student> students = new ArrayList<>();

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
        Student newStudent= studentRepository.save(student);
        return newStudent;
    }

    @Override
    public Student findByUsername(String username) {
        Student student= studentRepository.findByUsername(username);
        return student;
    }

    @Override
    public Student rechargeCard(String username, double credits) {
        Student student = studentRepository.findByUsername(username);
        double credit = student.getCredit();
        double newCredit = credit + credits;
        student.setCredit(newCredit);
        studentRepository.save(student);
        return student;
    }


    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    //--- util methods --------------------------------------------------------

    public void init(String filePath) throws IOException {
        ClassPathResource cpr = new ClassPathResource("cli-users.json");
        students = objectMapper.readValue(cpr.getInputStream(), new TypeReference<List<Student>>() { });
    }

    private long getNextId() {
        long maxId = 0;
        for(Student student : students) {
            if (student.getId().longValue() > maxId) {
                maxId = student.getId().longValue();
            }
        }
        return maxId + 1;
    }


}
