package com.example.driftconsultcli.cafeteriaCLI.command;

import com.example.driftconsultcli.cafeteriaCLI.shell.ShellHelper;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
@AllArgsConstructor
public class CafeteriaCommand {
    private final ShellHelper shellHelper;
}