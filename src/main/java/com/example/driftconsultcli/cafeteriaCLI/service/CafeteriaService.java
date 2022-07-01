package com.example.driftconsultcli.cafeteriaCLI.service;

import com.example.driftconsultcli.cafeteriaCLI.models.Cafeteria;
import com.example.driftconsultcli.cafeteriaCLI.models.Student;

import java.util.List;

public interface CafeteriaService {

    public boolean itemExists(Long id);

    public List<Cafeteria> getAllMenuItems();

    public Cafeteria getMenuItemById(Long itemId);
}
