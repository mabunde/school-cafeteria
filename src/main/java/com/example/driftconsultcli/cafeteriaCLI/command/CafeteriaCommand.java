package com.example.driftconsultcli.cafeteriaCLI.command;

import com.example.driftconsultcli.cafeteriaCLI.shell.ShellHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

@ShellComponent
public class CafeteriaCommand {
    @Autowired
    private ShellHelper shellHelper;

    @ShellMethod("Display menu items in tables")
    public void menu() {
        Object[][] menuitems = new Object[][]{
                {"Item", "Cost in KSH"},
                {"Tea", 40.00},
                {"coffee", 50.00},
                {"Indian Pancakes", 70.00},
                {"Omelette", 150.00},
                {"Fried eggs", 100.00},
                {"Biscuits", 20.00}
        };
        TableModel model = new ArrayTableModel(menuitems);
        TableBuilder tableBuilder = new TableBuilder(model);

        shellHelper.printInfo("School Cafeteria Menu");
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        shellHelper.print(tableBuilder.build().render(80));
    }
}
