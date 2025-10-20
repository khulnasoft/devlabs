package com.khulnasoft.mavenbuild;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for FileProcessor functionality.
 * Demonstrates comprehensive testing of file processing operations.
 */
class FileProcessorTest {

    private FileProcessor fileProcessor;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileProcessor = new FileProcessor();
    }

    @Test
    @DisplayName("Should convert text to uppercase correctly")
    void testConvertToUpperCase() throws IOException {
        // Create test input file
        Path inputFile = tempDir.resolve("input.txt");
        String content = "Hello World\nThis is a test";
        Files.write(inputFile, content.getBytes());

        // Process file
        Path outputFile = tempDir.resolve("output.txt");
        fileProcessor.convertToUpperCase(inputFile.toString(), outputFile.toString());

        // Verify output
        String expected = "HELLO WORLD\nTHIS IS A TEST";
        String actual = Files.readString(outputFile);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should count words in file correctly")
    void testCountWords() throws IOException {
        // Create test file
        Path testFile = tempDir.resolve("test.txt");
        String content = "This is a simple test file with some words.";
        Files.write(testFile, content.getBytes());

        // Count words
        int wordCount = fileProcessor.countWords(testFile.toString());

        // Verify count (7 words)
        assertEquals(7, wordCount);
    }

    @Test
    @DisplayName("Should return zero for empty file")
    void testCountWordsEmptyFile() throws IOException {
        // Create empty file
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.createFile(emptyFile);

        // Count words
        int wordCount = fileProcessor.countWords(emptyFile.toString());

        // Verify count is zero
        assertEquals(0, wordCount);
    }

    @Test
    @DisplayName("Should count lines correctly")
    void testCountLines() throws IOException {
        // Create test file with multiple lines
        Path testFile = tempDir.resolve("multiline.txt");
        String content = "Line 1\nLine 2\nLine 3\n";
        Files.write(testFile, content.getBytes());

        // Count lines
        long lineCount = fileProcessor.countLines(testFile.toString());

        // Verify count (3 lines, excluding empty line at end)
        assertEquals(3, lineCount);
    }

    @Test
    @DisplayName("Should find files by extension")
    void testFindFilesByExtension() throws IOException {
        // Create test files
        Path txtFile1 = tempDir.resolve("test1.txt");
        Path txtFile2 = tempDir.resolve("test2.txt");
        Path javaFile = tempDir.resolve("Test.java");
        Path mdFile = tempDir.resolve("README.md");

        Files.createFile(txtFile1);
        Files.createFile(txtFile2);
        Files.createFile(javaFile);
        Files.createFile(mdFile);

        // Find .txt files
        List<Path> txtFiles = fileProcessor.findFilesByExtension(tempDir.toString(), "txt");

        // Verify found files
        assertEquals(2, txtFiles.size());
        assertTrue(txtFiles.contains(txtFile1));
        assertTrue(txtFiles.contains(txtFile2));
    }

    @Test
    @DisplayName("Should create backup with timestamp")
    void testCreateBackup() throws IOException {
        // Create original file
        Path originalFile = tempDir.resolve("original.txt");
        String content = "Original content";
        Files.write(originalFile, content.getBytes());

        // Create backup
        Path backupPath = fileProcessor.createBackup(originalFile.toString());

        // Verify backup exists and has content
        assertTrue(Files.exists(backupPath));
        String backupContent = Files.readString(backupPath);
        assertEquals(content, backupContent);
    }

    @Test
    @DisplayName("Should merge multiple files correctly")
    void testMergeFiles() throws IOException {
        // Create input files
        Path file1 = tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");

        String content1 = "Content of file 1";
        String content2 = "Content of file 2";

        Files.write(file1, content1.getBytes());
        Files.write(file2, content2.getBytes());

        // Merge files
        Path outputFile = tempDir.resolve("merged.txt");
        fileProcessor.mergeFiles(Arrays.asList(file1.toString(), file2.toString()), outputFile.toString());

        // Verify merged content
        String mergedContent = Files.readString(outputFile);
        String expected = content1 + "\n\n" + content2 + "\n";
        assertEquals(expected, mergedContent);
    }

    @Test
    @DisplayName("Should validate existing readable files")
    void testIsValidFile() throws IOException {
        // Create valid file
        Path validFile = tempDir.resolve("valid.txt");
        Files.createFile(validFile);

        // Test validation
        assertTrue(fileProcessor.isValidFile(validFile.toString()));

        // Test non-existent file
        assertFalse(fileProcessor.isValidFile(tempDir.resolve("nonexistent.txt").toString()));
    }

    @Test
    @DisplayName("Should get correct file size")
    void testGetFileSize() throws IOException {
        // Create file with known content
        Path testFile = tempDir.resolve("size_test.txt");
        String content = "Test content for size calculation";
        Files.write(testFile, content.getBytes());

        // Get file size
        long fileSize = fileProcessor.getFileSize(testFile.toString());

        // Verify size matches content length
        assertEquals(content.length(), fileSize);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "pattern", "content"})
    @DisplayName("Should find pattern in file")
    void testContainsPattern(String pattern) throws IOException {
        // Create file with pattern
        Path testFile = tempDir.resolve("pattern_test.txt");
        String content = "This is a test file with test pattern content";
        Files.write(testFile, content.getBytes());

        // Test pattern search
        assertTrue(fileProcessor.containsPattern(testFile.toString(), pattern));
    }

    @Test
    @DisplayName("Should return false for non-existent pattern")
    void testContainsPatternNotFound() throws IOException {
        // Create file without pattern
        Path testFile = tempDir.resolve("no_pattern.txt");
        String content = "This is a simple file";
        Files.write(testFile, content.getBytes());

        // Test pattern search
        assertFalse(fileProcessor.containsPattern(testFile.toString(), "nonexistent"));
    }

    @Test
    @DisplayName("Should handle non-existent file for word count")
    void testCountWordsNonExistentFile() {
        assertThrows(IOException.class,
                () -> fileProcessor.countWords("/nonexistent/file.txt"));
    }

    @Test
    @DisplayName("Should handle non-existent file for line count")
    void testCountLinesNonExistentFile() {
        assertThrows(IOException.class,
                () -> fileProcessor.countLines("/nonexistent/file.txt"));
    }
}
