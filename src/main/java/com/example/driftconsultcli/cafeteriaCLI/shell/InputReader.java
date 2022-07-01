package com.example.driftconsultcli.cafeteriaCLI.shell;

import lombok.AllArgsConstructor;
import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.*;


public class InputReader {
    @Autowired
    private  ShellHelper shellHelper;

    public static final Character DEFAULT_MASK = '*';

    private Character mask;
    private LineReader lineReader;

    public InputReader(LineReader lineReader, ShellHelper shellHelper) {
        this(lineReader, shellHelper, null);
    }
    public InputReader(LineReader lineReader, ShellHelper shellHelper, Character mask) {
        this.lineReader = lineReader;
        this.shellHelper = shellHelper;
        this.mask = mask != null ? mask : DEFAULT_MASK;
    }

    public String prompt(String  prompt) {
        return prompt(prompt, null, true);
    }

    public String prompt(String  prompt, String defaultValue) {
        return prompt(prompt, defaultValue, true);
    }

    public String prompt(String  prompt, String defaultValue, boolean echo) {
        String answer = "";
        if (echo) {
            answer = lineReader.readLine(prompt + ": ");
        } else {
            answer = lineReader.readLine(prompt + ": ", mask);
        }
        if (StringUtils.isEmpty(answer)) {
            return defaultValue;
        }
        return answer;
    }
    //--- select one option from the list of values --------------------

    public String selectFromList(String headingMessage, String promptMessage, Map<String, String> options, boolean ignoreCase, String defaultValue) {
        String answer;
        Set<String> allowedAnswers = new HashSet<>(options.keySet());
        if (defaultValue != null && !defaultValue.equals("")) {
            allowedAnswers.add("");
        }
        shellHelper.print(String.format("%s: ", headingMessage));
        do {
            for (Map.Entry<String, String> option: options.entrySet()) {
                String defaultMarker = null;
                if (defaultValue != null) {
                    if (option.getKey().equals(defaultValue)) {
                        defaultMarker = "*";
                    }
                }
                if (defaultMarker != null) {
                    shellHelper.printInfo(String.format("%s [%s] %s ", defaultMarker, option.getKey(), option.getValue()));
                } else {
                    shellHelper.print(String.format("  [%s] %s", option.getKey(), option.getValue()));
                }
            }
            answer = lineReader.readLine(String.format("%s: ", promptMessage));
        } while (!containsString(allowedAnswers, answer, ignoreCase) && "" != answer);
        if (StringUtils.isEmpty(answer) && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }
    public String promptWithOptions(String prompt, String defaultValue, List<String> optionsAsList) {
        String answer;
        List<String> allowedAnswers = new ArrayList<>(optionsAsList);
        if (StringUtils.hasText(defaultValue)) {
            allowedAnswers.add("");
        }
        do {
            answer = lineReader.readLine(String.format("%s %s: ", prompt, formatOptions(defaultValue, optionsAsList)));
        } while (!allowedAnswers.contains(answer) && !"".equals(answer));

        if (answer.isEmpty() && allowedAnswers.contains("")) {
            return defaultValue;
        }
        return answer;
    }
    private boolean containsString(Set <String> l, String s, boolean ignoreCase){
        if (!ignoreCase) {
            return l.contains(s);
        }
        Iterator<String> it = l.iterator();
        while(it.hasNext()) {
            if(it.next().equalsIgnoreCase(s))
                return true;
        }
        return false;
    }
    private List<String> formatOptions(String defaultValue, List<String> optionsAsList) {
        List<String> result = new ArrayList<>();
        for (String option : optionsAsList) {
            String val = option;
            if ("".equals(option) || option == null) {
                val = "''";
            }
            if (defaultValue != null) {
                if (defaultValue.equals(option) || (defaultValue.equals("") && option == null)) {
                    val = shellHelper.getInfoMessage(val);
                }
            }
            result.add(val);
        }
        return result;
    }
}
