package com.example.driftconsultcli.cafeteriaCLI.command;

import com.example.driftconsultcli.cafeteriaCLI.models.Cafeteria;
import com.example.driftconsultcli.cafeteriaCLI.models.Student;
import com.example.driftconsultcli.cafeteriaCLI.repository.CafeteriaRepository;
import com.example.driftconsultcli.cafeteriaCLI.service.CafeteriaService;
import com.example.driftconsultcli.cafeteriaCLI.service.StudentService;
import com.example.driftconsultcli.cafeteriaCLI.shell.InputReader;
import com.example.driftconsultcli.cafeteriaCLI.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent

public class CafeteriaCommand {
    @Autowired
    private ShellHelper shellHelper;
    @Autowired
    private InputReader inputReader;
    @Autowired
    private CafeteriaService cafeteriaService;
    @Autowired
    private StudentService studentService;


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

    @ShellMethod("buy items from school cafeteria menu")
    public void buy(@ShellOption(value = {"-U",
            "--username"}) String username) {

        Student student = studentService.findByUsername(username);
        if (student == null) {
            shellHelper.printWarning(
                    String.format("Failed, student with username %s does not exist. Aborted!!", username));
            return;
        }

        String order ="Y";
        List<Cafeteria> items = new ArrayList<>();
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

        while(order.equalsIgnoreCase("Y")){
            String itemCode = inputReader.prompt("Enter the item code of the item purchased");
            Long id = Long.valueOf(itemCode);
            Cafeteria item = cafeteriaService.getMenuItemById(id);
            items.add(item);
            double total =0.00;
            String selecteditem="";
            for (Cafeteria item1 : items) {
                selecteditem=item1.getItemName();
                total += item1.getItemPrice();
            }

            int quantity = Integer.parseInt(inputReader.prompt("Enter the quantity of the item purchased"));
            double cost = quantity * total;
            shellHelper.printInfo(String.format("Your order  "+selecteditem+ " *" +quantity+
                            "  ==  " + "ksh"+cost ));
            order = inputReader.promptWithOptions("Buy another item ?", "Y", Arrays.asList("y", "n"));

        }
    }
}
