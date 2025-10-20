package com.khulnasoft.mavenbuild;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * File Processor utility class for Maven build tools.
 * Provides file manipulation and processing capabilities.
 */
public class FileProcessor {

    /**
     * Processes text files by converting content to uppercase.
     * @param inputFile input file path
     * @param outputFile output file path
     * @throws IOException if file operations fail
     */
    public void convertToUpperCase(String inputFile, String outputFile) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(inputFile));
        List<String> upperCaseLines = lines.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        Files.write(Paths.get(outputFile), upperCaseLines);
    }

    /**
     * Counts words in a text file.
     * @param filePath path to the file
     * @return word count
     * @throws IOException if file operations fail
     */
    public int countWords(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        if (content.trim().isEmpty()) {
            return 0;
        }

        String[] words = content.trim().split("\\s+");
        return words.length;
    }

    /**
     * Counts lines in a file.
     * @param filePath path to the file
     * @return line count
     * @throws IOException if file operations fail
     */
    public long countLines(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath)).count();
    }

    /**
     * Finds files with specific extension in a directory.
     * @param directory directory to search
     * @param extension file extension (without dot)
     * @return list of matching files
     * @throws IOException if directory operations fail
     */
    public List<Path> findFilesByExtension(String directory, String extension) throws IOException {
        return Files.walk(Paths.get(directory))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith("." + extension))
                .collect(Collectors.toList());
    }

    /**
     * Creates a backup of a file with timestamp.
     * @param filePath path to the original file
     * @return path to the backup file
     * @throws IOException if file operations fail
     */
    public Path createBackup(String filePath) throws IOException {
        Path originalPath = Paths.get(filePath);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String backupName = originalPath.getFileName().toString() + ".backup." + timestamp;
        Path backupPath = originalPath.getParent().resolve(backupName);

        Files.copy(originalPath, backupPath);
        return backupPath;
    }

    /**
     * Merges multiple text files into one.
     * @param inputFiles list of input file paths
     * @param outputFile output file path
     * @throws IOException if file operations fail
     */
    public void mergeFiles(List<String> inputFiles, String outputFile) throws IOException {
        List<String> allLines = new ArrayList<>();

        for (String inputFile : inputFiles) {
            List<String> lines = Files.readAllLines(Paths.get(inputFile));
            allLines.addAll(lines);
            allLines.add(""); // Add empty line between files
        }

        Files.write(Paths.get(outputFile), allLines);
    }

    /**
     * Validates if a file exists and is readable.
     * @param filePath path to the file
     * @return true if file exists and is readable
     */
    public boolean isValidFile(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path) && Files.isRegularFile(path) && Files.isReadable(path);
    }

    /**
     * Gets file size in bytes.
     * @param filePath path to the file
     * @return file size in bytes
     * @throws IOException if file operations fail
     */
    public long getFileSize(String filePath) throws IOException {
        return Files.size(Paths.get(filePath));
    }

    /**
     * Searches for a pattern in a file.
     * @param filePath path to the file
     * @param pattern pattern to search for
     * @return true if pattern is found
     * @throws IOException if file operations fail
     */
    public boolean containsPattern(String filePath, String pattern) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        return content.contains(pattern);
    }
}
