package io.github.kotlandpolito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static List<String[]> parseCsv(String filename) throws FileNotFoundException {
        List<String[]> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(!line.isEmpty()){
                    lines.add(line.split(","));
                }
            }
        }
        return lines;
    }

    public static void writeCsv(String filename, List<String[]> lines) throws FileNotFoundException {
        File csvOutputFile = new File(filename);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            lines.stream()
                    .map(Utils::lineToCSV)
                    .forEach(pw::println);
        }
    }

    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static String lineToCSV(String[] line) {
        return Stream.of(line)
                .map(Utils::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public static double[] stringArrayToDoubleArray(String[] strings){
        return Arrays.stream(strings).mapToDouble(Double::parseDouble).toArray();
    }

    public static String[] doubleArrayToStringArray(double[] doubles){
        return Arrays.stream(doubles).mapToObj(String::valueOf).toArray(String[]::new);
    }

}
