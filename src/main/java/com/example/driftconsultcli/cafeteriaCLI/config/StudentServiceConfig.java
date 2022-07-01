package com.example.driftconsultcli.cafeteriaCLI.config;

import com.example.driftconsultcli.cafeteriaCLI.service.StudentService;
import com.example.driftconsultcli.cafeteriaCLI.service.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public class StudentServiceConfig {
    @Bean
    public StudentService studentService(ObjectMapper objectMapper) throws IOException {
        StudentServiceImpl studentService = new StudentServiceImpl();
        studentService.setObjectMapper(objectMapper);
        studentService.init("cli-student.json");
        return studentService;
    }
}
