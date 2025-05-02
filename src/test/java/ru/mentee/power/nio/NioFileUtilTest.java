package ru.mentee.power.nio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Тесты утилиты копирования NioFileUtil")
class NioFileUtilTest { // Переименовали тест

  @TempDir
  Path tempDir;

  private Path sourcePath;
  private Path destinationPath;
  private String testContent = "Тестовое содержимое для NioFileUtil";

  @BeforeEach
  void setUp() throws IOException {
    sourcePath = tempDir.resolve("source_util_test.txt");
    destinationPath = tempDir.resolve("destination_util_test.txt");
    Files.writeString(sourcePath, testContent, StandardCharsets.UTF_8);
  }

  @Test
  @DisplayName("copyFile должен успешно копировать существующий файл")
  void copyFileShouldCopyExistingFile() throws IOException {

    assertThat(destinationPath).doesNotExist();

    NioFileUtil.copyFile(sourcePath, destinationPath);

    assertThat(destinationPath).exists();
    assertThat(Files.readString(destinationPath, StandardCharsets.UTF_8)).isEqualTo(testContent);
  }

  @Test
  @DisplayName("copyFile должен перезаписать существующий целевой файл")
  void copyFileShouldOverwriteExistingDestinationFile() throws IOException {
    String initialDestContent = "Старое содержимое";
    Files.writeString(destinationPath, initialDestContent, StandardCharsets.UTF_8);
    assertThat(destinationPath).hasContent(initialDestContent);

    NioFileUtil.copyFile(sourcePath, destinationPath);
    assertThat(destinationPath).exists();
    assertThat(destinationPath).hasContent(testContent);
  }

  @Test
  @DisplayName("copyFile должен выбросить NoSuchFileException, если исходный файл не существует")
  void copyFileShouldThrowExceptionIfSourceDoesNotExist() throws IOException {
    Files.delete(sourcePath);
    assertThat(sourcePath).doesNotExist();

    assertThatThrownBy(() -> NioFileUtil.copyFile(sourcePath, destinationPath))
        .isInstanceOf(NoSuchFileException.class);
    assertThat(destinationPath).doesNotExist();
  }
}