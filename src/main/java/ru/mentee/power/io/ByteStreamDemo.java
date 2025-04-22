package ru.mentee.power.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets; // Используем стандартные кодировки

public class ByteStreamDemo {

    public static void main(String[] args) {
        String sourceFileName = "source_utf8.txt"; // Создай этот файл в UTF-8
        String destinationFileName = "destination_bytes.txt";

        // --- Запись байтов в файл ---

        try (FileOutputStream fos = new FileOutputStream(destinationFileName)) {
            String data = "Привет, мир байтов! Hello, byte world!";

            byte[] bytesToWrite = data.getBytes(StandardCharsets.UTF_8);

            fos.write(bytesToWrite);
            System.out.println("Данные успешно записаны в файл: " + destinationFileName);

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }

        // --- Чтение байтов из файла (побайтово) ---
        System.out.println("\n--- Чтение побайтово из " + sourceFileName + " ---");
        try (FileInputStream fis = new FileInputStream(sourceFileName)) {

            int byteRead;

            while ((byteRead = fis.read()) != -1) {
                System.out.print(byteRead + " ");
            }
            System.out.println("\nПобайтовое чтение завершено.");

        }
        catch (FileNotFoundException e){
            System.out.println("Ошибка при поиске файла: " + e);
        }
        catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        // --- Чтение байтов в массив ---
        System.out.println("\n--- Чтение в массив из " + destinationFileName + " ---");
        try (FileInputStream fisForArray = new FileInputStream(destinationFileName)) {
            int availableBytes = fisForArray.available();
            byte[] buffer = new byte[availableBytes];

            int bytesActuallyRead = fisForArray.read(buffer);

            if (bytesActuallyRead > 0) {
                String content = new String(buffer, 0, bytesActuallyRead, StandardCharsets.UTF_8);
                System.out.println("Прочитано байт: " + bytesActuallyRead);
                System.out.println("Содержимое: '" + content + "'");
            } else {
                System.out.println("Файл пуст.");
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла в массив: " + e.getMessage());
        }
    }
}