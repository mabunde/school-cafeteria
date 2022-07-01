package com.example.driftconsultcli.cafeteriaCLI.command;

import com.example.driftconsultcli.cafeteriaCLI.models.Gender;
import com.example.driftconsultcli.cafeteriaCLI.models.Student;
import com.example.driftconsultcli.cafeteriaCLI.service.StudentService;
import com.example.driftconsultcli.cafeteriaCLI.shell.InputReader;
import com.example.driftconsultcli.cafeteriaCLI.shell.ShellHelper;
import com.example.driftconsultcli.cafeteriaCLI.table.BeanTableModelBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.*;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ShellComponent
public class StudentCommand {
    @Autowired
    private  ShellHelper shellHelper;
    @Autowired
    private  InputReader inputReader;
    @Autowired
    private  StudentService studentService;
    @Autowired
    private ObjectMapper objectMapper;


    @ShellMethod("Create new student with supplied username")
    public void enrollstudent(@ShellOption({"-U", "--username"}) String username) {
        if (studentService.existsByUsername(username)) {
            shellHelper.printError(String.format("student with username='%s' already exists --> ABORTING", username));
            return;
        }
        Student student = new Student();
        student.setUsername(username);

        // 1. read student's first name --------------------------------------------
        do {
            String firstName = inputReader.prompt("First name");
            if (StringUtils.hasText(firstName)) {
                student.setFirstName(firstName);
            } else {
                shellHelper.printWarning("student's first name CAN NOT be empty string? Please enter valid value!");
            }
        } while (student.getFirstName() == null);

        // 1. read student's last name --------------------------------------------
        do {
            String lastName = inputReader.prompt("Last name");
            if (StringUtils.hasText(lastName)) {
                student.setLastName(lastName);
            } else {
                shellHelper.printWarning("Student's last name CANNOT be empty? Please enter valid value!");
            }
        } while (student.getLastName() == null);

        // 3. read student's Gender ----------------------------------------------
        Map<String, String> options = new HashMap<>();
        options.put("M", Gender.MALE.name());
        options.put("F", Gender.FEMALE.name() );
        options.put("D", Gender.DIVERSE.name());
        String genderValue = inputReader.selectFromList("Gender", "Please enter one of the [] values", options, true, null);
        Gender gender = Gender.valueOf(options.get(genderValue.toUpperCase()));
        student.setGender(gender);

        // Print student's input -------------------------------------------------
        shellHelper.printInfo("\nCreating new student:");
        shellHelper.print("\nUsername: " + student.getUsername());
        shellHelper.print("Firstname: " + student.getFirstName());
        shellHelper.print("Lastname: " + student.getLastName());
        shellHelper.print("Gender: " + student.getGender()+ "\n");


        Student createdStudent = studentService.addStudent(student);
        shellHelper.printSuccess("Created student with id=" + createdStudent.getId());
    }

    @ShellMethod("Display list of students enrolled")
    public void listStudents() {
        List<Student> students = studentService.getAllStudents();

        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("username", "Username");
        headers.put("firstName", "First name");
        headers.put("lastName", "Last name");
        headers.put("gender", "Gender");
        headers.put("credit", "Credit");
        TableModel model = new BeanListTableModel<>(students, headers);

        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        shellHelper.print(tableBuilder.build().render(80));
    }
    @ShellMethod("Display student's meal card with supplied username")
    public void mealcard(@ShellOption({"-U", "--username"}) String username) {
        Student student = studentService.findByUsername(username);
        if (student == null) {
            shellHelper.printWarning("No student with the supplied username could be found?!");
            return;
        }
        displaystudent(student);
    }

    private void displaystudent(Student student) {
        LinkedHashMap<String, Object> labels = new LinkedHashMap<>();
        labels.put("id", "Id");
        labels.put("username", "username");
        labels.put("firstName", "first name");
        labels.put("lastName", "last name");
        labels.put("gender", "Gender");

        String[] header = new String[] {"Property", "Value"};
        BeanTableModelBuilder builder = new BeanTableModelBuilder(student, objectMapper);
        TableModel model = builder.withLabels(labels).withHeader(header).build();

        TableBuilder tableBuilder = new TableBuilder(model);

        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        tableBuilder.on(CellMatchers.column(0)).addSizer(new AbsoluteWidthSizeConstraints(20));
        tableBuilder.on(CellMatchers.column(1)).addSizer(new AbsoluteWidthSizeConstraints(30));
        shellHelper.print(tableBuilder.build().render(80));
    }
    @ShellMethod("Recharge student's card")
    public void recharge(@ShellOption({"-U", "--username"}) String username) {
        Student student = studentService.findByUsername(username);
        if (student == null) {
            shellHelper.printWarning("No student with the supplied username could be found?!");
            return;
        }
        return;


    }

}
