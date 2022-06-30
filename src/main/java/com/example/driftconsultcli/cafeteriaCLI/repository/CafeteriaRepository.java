package com.example.driftconsultcli.cafeteriaCLI.repository;

import com.example.driftconsultcli.cafeteriaCLI.models.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeteriaRepository extends JpaRepository<Cafeteria, Long> {
}
