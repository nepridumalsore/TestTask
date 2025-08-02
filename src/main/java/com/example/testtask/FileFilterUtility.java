package com.example.testtask;

import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileFilterUtility {
    private static final String DEFAULT_INTEGERS_FILE = "integers.txt";
    private static final String DEFAULT_FLOATS_FILE = "floats.txt";
    private static final String DEFAULT_STRINGS_FILE = "strings.txt";

    private static class Stats {
        long count = 0;
        Double min = null;
        Double max = null;
        double sum = 0.0;
        Integer minLength = null;
        Integer maxLength = null;

        void updateNumber(double value) {
            count++;
            sum += value;
            if (min == null || value < min) min = value;
            if (max == null || value > max) max = value;
        }

        void updateString(int length) {
            count++;
            if (minLength == null || length < minLength) minLength = length;
            if (maxLength == null || length > maxLength) maxLength = length;
        }
    }

    public static void main(String[] args) {
        try {
            processArguments(args);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void processArguments(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("No input files specified");
        }

        boolean appendMode = false;
        boolean shortStats = false;
        boolean fullStats = false;
        String outputPath = "";
        String prefix = "";
        List<String> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    shortStats = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                case "-o":
                    if (++i >= args.length) throw new IllegalArgumentException("-o requires output path");
                    outputPath = args[i];
                    break;
                case "-p":
                    if (++i >= args.length) throw new IllegalArgumentException("-p requires prefix");
                    prefix = args[i];
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }

        if (inputFiles.isEmpty()) {
            throw new IllegalArgumentException("No input files specified");
        }

        if (!outputPath.isEmpty()) {
            Path dir = Paths.get(outputPath);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            if (!Files.isDirectory(dir)) {
                throw new IllegalArgumentException("Output path is not a directory: " + outputPath);
            }
        }

        Stats intStats = new Stats();
        Stats floatStats = new Stats();
        Stats stringStats = new Stats();

        List<String> integers = new ArrayList<>();
        List<String> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String file : inputFiles) {
            processFile(file, integers, floats, strings, intStats, floatStats, stringStats);
        }

        writeResults(outputPath, prefix, appendMode, integers, floats, strings);

        printStatistics(shortStats, fullStats, intStats, floatStats, stringStats);
    }

    private static void processFile(String filePath, List<String> integers, List<String> floats,
                                    List<String> strings, Stats intStats, Stats floatStats,
                                    Stats stringStats) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (StringUtils.isBlank(line)) continue;

                try {
                    if (line.matches("^-?\\d+$")) {
                        long value = Long.parseLong(line);
                        integers.add(line);
                        intStats.updateNumber(value);
                    } else if (line.matches("^-?\\d*\\.\\d+[eE][+-]?\\d+$|^-?\\d*\\.\\d+$")) {
                        double value = Double.parseDouble(line);
                        floats.add(line);
                        floatStats.updateNumber(value);
                    } else {
                        strings.add(line);
                        stringStats.updateString(line.length());
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Cannot parse line in " + filePath + ": " + line);
                    strings.add(line);
                    stringStats.updateString(line.length());
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: Cannot read file " + filePath + ": " + e.getMessage());
        }
    }

    private static void writeResults(String outputPath, String prefix, boolean appendMode,
                                     List<String> integers, List<String> floats, List<String> strings)
            throws IOException {
        if (!integers.isEmpty()) {
            writeFile(getOutputFilePath(outputPath, prefix, DEFAULT_INTEGERS_FILE), integers, appendMode);
        }
        if (!floats.isEmpty()) {
            writeFile(getOutputFilePath(outputPath, prefix, DEFAULT_FLOATS_FILE), floats, appendMode);
        }
        if (!strings.isEmpty()) {
            writeFile(getOutputFilePath(outputPath, prefix, DEFAULT_STRINGS_FILE), strings, appendMode);
        }
    }

    private static String getOutputFilePath(String outputPath, String prefix, String defaultName) {
        String fileName = prefix + defaultName;
        return outputPath.isEmpty() ? fileName : Paths.get(outputPath, fileName).toString();
    }

    private static void writeFile(String filePath, List<String> data, boolean appendMode)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, appendMode))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static void printStatistics(boolean shortStats, boolean fullStats, Stats intStats,
                                        Stats floatStats, Stats stringStats) {
        if (!shortStats && !fullStats) return;

        System.out.println("Statistics:");
        if (intStats.count > 0) {
            System.out.println("Integers: " + intStats.count);
            if (fullStats) {
                System.out.printf("  Min: %.0f%n", intStats.min);
                System.out.printf("  Max: %.0f%n", intStats.max);
                System.out.printf("  Sum: %.0f%n", intStats.sum);
                System.out.printf("  Average: %.2f%n", intStats.sum / intStats.count);
            }
        }
        if (floatStats.count > 0) {
            System.out.println("Floats: " + floatStats.count);
            if (fullStats) {
                System.out.printf("  Min: %s%n", floatStats.min);
                System.out.printf("  Max: %s%n", floatStats.max);
                System.out.printf("  Sum: %s%n", floatStats.sum);
                System.out.printf("  Average: %s%n", floatStats.sum / floatStats.count);
            }
        }
        if (stringStats.count > 0) {
            System.out.println("Strings: " + stringStats.count);
            if (fullStats) {
                System.out.println("  Min length: " + stringStats.minLength);
                System.out.println("  Max length: " + stringStats.maxLength);
            }
        }
    }
}
