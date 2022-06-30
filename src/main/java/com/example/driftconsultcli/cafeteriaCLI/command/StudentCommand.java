package com.example.driftconsultcli.cafeteriaCLI.command;

import com.example.driftconsultcli.cafeteriaCLI.models.Gender;
import com.example.driftconsultcli.cafeteriaCLI.models.Student;
import com.example.driftconsultcli.cafeteriaCLI.service.StudentService;
import com.example.driftconsultcli.cafeteriaCLI.shell.InputReader;
import com.example.driftconsultcli.cafeteriaCLI.shell.ShellHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class StudentCommand {
    @Autowired
    private  ShellHelper shellHelper;
    @Autowired
    private  InputReader inputReader;
    @Autowired
    private  StudentService studentService;


    @ShellMethod("Create new student with supplied username")
    public void createUser(@ShellOption({"-U", "--username"}) String username) {
        if (studentService.existsByUsername(username)) {
            shellHelper.printError(String.format("User with username='%s' already exists --> ABORTING", username));
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
                shellHelper.printWarning("User's first name CAN NOT be empty string? Please enter valid value!");
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

        // 3. read user's Gender ----------------------------------------------
        Map<String, String> options = new HashMap<>();
        options.put("M", Gender.MALE.name());
        options.put("F", Gender.FEMALE.name() );
        options.put("D", Gender.DIVERSE.name());
        String genderValue = inputReader.selectFromList("Gender", "Please enter one of the [] values", options, true, null);
        Gender gender = Gender.valueOf(options.get(genderValue.toUpperCase()));
        student.setGender(gender);

        // Print user's input -------------------------------------------------
        shellHelper.printInfo("\nCreating new student:");
        shellHelper.print("\nUsername: " + student.getUsername());
        shellHelper.print("Firstname: " + student.getFirstName());
        shellHelper.print("Lastname: " + student.getLastName());
        shellHelper.print("Gender: " + student.getGender()+ "\n");


        Student createdStudent = studentService.addStudent(student);
        shellHelper.printSuccess("Created student with id=" + createdStudent.getId());
    }
}
