package com.example.driftconsultcli.cafeteriaCLI.repository;

import com.example.driftconsultcli.cafeteriaCLI.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    public boolean existsByUsername(String username);
    Student findByUsername(String username);
}
