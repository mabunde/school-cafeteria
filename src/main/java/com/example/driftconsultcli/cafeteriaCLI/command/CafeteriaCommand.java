package com.example.driftconsultcli.cafeteriaCLI.command;

import com.example.driftconsultcli.cafeteriaCLI.models.Cafeteria;
import com.example.driftconsultcli.cafeteriaCLI.repository.CafeteriaRepository;
import com.example.driftconsultcli.cafeteriaCLI.service.CafeteriaService;
import com.example.driftconsultcli.cafeteriaCLI.shell.InputReader;
import com.example.driftconsultcli.cafeteriaCLI.shell.ShellHelper;
import com.example.driftconsultcli.cafeteriaCLI.table.BeanTableModelBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
public class CafeteriaCommand {
    @Autowired
    private ShellHelper shellHelper;
    @Autowired
    private CafeteriaService cafeteriaService;

    @ShellMethod("Display menu items in tables")
    public void menu() {
        List<Cafeteria> menuitems = cafeteriaService.getAllMenuItems();
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();

        headers.put("id", "Item Code");
        headers.put("itemName", "Item");
        headers.put("itemPrice", "cost in ksh");

        shellHelper.printInfo("School cafeteria menu");
        TableModel model = new BeanListTableModel<>(menuitems, headers);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        tableBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(20));
        tableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(30));
        shellHelper.print(tableBuilder.build().render(80));
    }

}
