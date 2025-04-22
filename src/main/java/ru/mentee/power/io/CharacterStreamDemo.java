package ru.mentee.power.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CharacterStreamDemo {

    public static void main(String[] args) {
        // Узнаем кодировку по умолчанию в системе
        System.out.println("Кодировка по умолчанию: " + Charset.defaultCharset());

        String textFileName = "example_chars.txt";
        String textToWrite = "Привет, мир символов!\nHello, char world!\nТретья строка.";
        File file = new File(textFileName);

        // --- Запись символов в файл ---
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(textToWrite);
            System.out.println("Текст успешно записан в файл: " + textFileName);

            // Проверяем, что файл действительно создан и доступен
            if (!file.exists()) {
                throw new IOException("Файл не был создан после записи");
            }
            if (!file.canRead()) {
                throw new IOException("Файл недоступен для чтения после создания");
            }

        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
            return;
        }

        // --- Чтение символов из файла ---
        System.out.println("\n--- Чтение посимвольно из " + textFileName + " ---");
        if (!checkFileAccess(file)) {
            return;
        }

        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            int characterRead;
            while ((characterRead = reader.read()) != -1) {
                System.out.print((char) characterRead);
            }
            System.out.println("\nПосимвольное чтение завершено.");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        // --- Чтение символов в массив ---
        System.out.println("\n--- Чтение в массив символов из " + textFileName + " ---");
        if (!checkFileAccess(file)) {
            return;
        }

        try (FileReader readerForArray = new FileReader(file, StandardCharsets.UTF_8)) {
            char[] buffer = new char[1024];
            int charsActuallyRead = readerForArray.read(buffer);

            if (charsActuallyRead > 0) {
                String content = new String(buffer, 0, charsActuallyRead);
                System.out.println("Прочитано символов: " + charsActuallyRead);
                System.out.println("Содержимое:\n" + content);
            } else {
                System.out.println("Файл пуст.");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла в массив: " + e.getMessage());
        }
    }

    private static boolean checkFileAccess(File file) {
        if (!file.exists()) {
            System.err.println("Ошибка: Файл не существует - " + file.getAbsolutePath());
            return false;
        }
        if (!file.canRead()) {
            System.err.println("Ошибка: Нет прав на чтение файла - " + file.getAbsolutePath());
            return false;
        }
        return true;
    }
}