package com.example.driftconsultcli.cafeteriaCLI;

import com.example.driftconsultcli.cafeteriaCLI.models.Cafeteria;
import com.example.driftconsultcli.cafeteriaCLI.repository.CafeteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private CafeteriaRepository cafeteriaRepository;
    @Override
    public void run(String... args) throws Exception {

        List<Cafeteria> menu = new ArrayList<>();

        Cafeteria tea = new Cafeteria("Tea", 40.00);
        Cafeteria coffee = new Cafeteria("Coffee", 50.00);
        Cafeteria indianPancakes = new Cafeteria("Indian Pancakes", 70.00);
        Cafeteria omelette = new Cafeteria("Omelette", 150.00);
        Cafeteria friedEggs = new Cafeteria("Fried Eggs", 100.00);
        Cafeteria biscuits = new Cafeteria("Biscuit", 20.00);

        menu.add(tea);
        menu.add(coffee);
        menu.add(indianPancakes);
        menu.add(omelette);
        menu.add(friedEggs);
        menu.add(biscuits);

        cafeteriaRepository.saveAll(menu);
    }
}
