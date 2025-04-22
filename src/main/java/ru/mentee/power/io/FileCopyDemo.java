package ru.mentee.power.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class FileCopyDemo {

    public static void main(String[] args) {
        String sourceFileName = "source.txt";
        String destinationFileName = "copy_of_source.txt";
        Path sourcePath = Paths.get(sourceFileName);

        if (!Files.exists(sourcePath)) {
            try {
                Files.writeString(sourcePath, "Текст по умолчанию...", StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.err.println("Ошибка при создании файла: " + e.getMessage());
            }
        }

        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];

        try (FileInputStream fis = new FileInputStream(sourceFileName);
             FileOutputStream fos = new FileOutputStream(destinationFileName)) {

            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Файл успешно скопирован из " + sourceFileName + " в " + destinationFileName);

        } catch (IOException e) {
            System.err.println("Ошибка при копировании файла: " + e.getMessage());
        }
    }
}