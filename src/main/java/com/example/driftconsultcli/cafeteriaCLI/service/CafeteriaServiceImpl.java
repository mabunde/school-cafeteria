package com.example.driftconsultcli.cafeteriaCLI.service;

import com.example.driftconsultcli.cafeteriaCLI.models.Cafeteria;
import com.example.driftconsultcli.cafeteriaCLI.models.Student;
import com.example.driftconsultcli.cafeteriaCLI.repository.CafeteriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CafeteriaServiceImpl implements CafeteriaService{
    private final CafeteriaRepository cafeteriaRepository;

    @Override
    public boolean itemExists(Long id) {
        return cafeteriaRepository.existsById(id);
    }

    @Override
    public List<Cafeteria> getAllMenuItems() {
        return cafeteriaRepository.findAll();
    }


    @Override
    public Cafeteria getMenuItemById(Long itemId) {
        Optional<Cafeteria> item = cafeteriaRepository.findById(itemId);

        if (item.isPresent()) {
            return item.get();
        }
        return null;
    }

}
