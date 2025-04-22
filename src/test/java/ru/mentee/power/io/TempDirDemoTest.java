package ru.mentee.power.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class TempDirDemoTest {

    @Test
    void testWithTempDir(@TempDir Path tempDirPath) throws IOException {
        System.out.println("Временная директория: " + tempDirPath);
        Path testFile = tempDirPath.resolve("my_test_file.txt");
        Files.writeString(testFile, "Hello from temp file!");
        assertThat(testFile).exists().hasContent("Hello from temp file!");
    }

    @TempDir File anotherTempDir; // Можно и на поле

    @Test
    void testWithTempDirField() throws IOException {
        assertThat(anotherTempDir).isDirectory();
        Path testFile2 = anotherTempDir.toPath().resolve("another.log");
        Files.createFile(testFile2);
        assertThat(testFile2).exists();
    }
}
