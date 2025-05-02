package ru.mentee.power.nio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

class NioFileUtil {

  public static void copyFile(Path source, Path destination) throws IOException {
    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    System.out.println("(Внутри NioFileUtil) Копирование из " + source + " в " + destination);
  }
}

public class FilesCopyDemo {

  public static void main(String[] args) {
    Path sourcePath = Paths.get("source_files_copy.txt");
    Path destinationPath = Paths.get("destination_files_copy.txt");

    try {

      if (Files.notExists(sourcePath)) {
        System.out.println("Созднаём исходный файл: " + sourcePath);
        String defaultContent = "Это содержимое файла по умолчанию, \nсозданного программой.";
        Files.writeString(sourcePath, defaultContent, StandardCharsets.UTF_8);
      }

      System.out.println("Вызываем NioFileUtil.copyFile...");
      NioFileUtil.copyFile(sourcePath, destinationPath);

      System.out.println("Файл успешно скопирован из " + sourcePath + " в " + destinationPath);

    } catch (IOException e) {
      System.err.println("Ошибка при копировании или создании файла: " + e.getMessage());
    }

    try {
      Files.deleteIfExists(destinationPath);
    } catch (IOException e) {
    }
  }
}