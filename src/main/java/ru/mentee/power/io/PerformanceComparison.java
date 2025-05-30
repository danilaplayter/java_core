package ru.mentee.power.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PerformanceComparison {

  private static final String SOURCE_FILE = "large_test_file.bin";
  private static final String DEST_BUFFERED = "copy_buffered.bin";
  private static final String DEST_UNBUFFERED = "copy_unbuffered.bin";
  private static final long FILE_SIZE_MB = 20; // Размер файла в МБ
  private static final long FILE_SIZE_BYTES = FILE_SIZE_MB * 1024 * 1024;
  private static final int BUFFER_SIZE = 8192; // 8KB буфер

  public static void main(String[] args) {
    try {
      createLargeBinaryFile(SOURCE_FILE, FILE_SIZE_BYTES);

      System.out.println("Начинаем копирование (" + FILE_SIZE_MB + " MB)...\n");

      // Буферизованное копирование
      long startTimeBuffered = System.nanoTime();
      copyBuffered(SOURCE_FILE, DEST_BUFFERED);
      long endTimeBuffered = System.nanoTime();
      long durationBuffered = (endTimeBuffered - startTimeBuffered) / 1_000_000;
      System.out.println("Буферизованное копирование:   " + durationBuffered + " мс");

      // Небуферизованное копирование
      long startTimeUnbuffered = System.nanoTime();
      copyUnbuffered(SOURCE_FILE, DEST_UNBUFFERED);
      long endTimeUnbuffered = System.nanoTime();
      long durationUnbuffered = (endTimeUnbuffered - startTimeUnbuffered) / 1_000_000;
      System.out.println("Небуферизованное (побайтно): " + durationUnbuffered + " мс");

      // Проверка корректности
      System.out.println("\nПроверка корректности копий...");
      Path sourcePath = Paths.get(SOURCE_FILE);
      Path bufferedPath = Paths.get(DEST_BUFFERED);
      Path unbufferedPath = Paths.get(DEST_UNBUFFERED);

      long mismatchBuffered = Files.mismatch(sourcePath, bufferedPath);
      long mismatchUnbuffered = Files.mismatch(sourcePath, unbufferedPath);

      System.out.println("Буферизованная копия совпадает: " + (mismatchBuffered == -1L));
      System.out.println("Небуферизованная копия совпадает: " + (mismatchUnbuffered == -1L));

    } catch (IOException e) {
      System.err.println("Ошибка ввода-вывода: " + e.getMessage());
      e.printStackTrace();
    } finally {
      // Очистка
      System.out.println("\nОчистка тестовых файлов...");
      deleteFileIfExists(SOURCE_FILE);
      deleteFileIfExists(DEST_BUFFERED);
      deleteFileIfExists(DEST_UNBUFFERED);
      System.out.println("Очистка завершена.");
    }
  }

  // Буферизированное копирование
  static void copyBuffered(String source, String dest) throws IOException {
    try (InputStream bis = new BufferedInputStream(new FileInputStream(source),
        BUFFER_SIZE); OutputStream bos = new BufferedOutputStream(new FileOutputStream(dest),
        BUFFER_SIZE)) {
      byte[] buffer = new byte[BUFFER_SIZE];
      int bytesRead;
      while ((bytesRead = bis.read(buffer)) != -1) {
        bos.write(buffer, 0, bytesRead);
      }
    }
  }

  // Небуферизированное (побайтное) копирование
  static void copyUnbuffered(String source, String dest) throws IOException {
    try (InputStream fis = new FileInputStream(source); OutputStream fos = new FileOutputStream(
        dest)) {
      int byteRead;
      while ((byteRead = fis.read()) != -1) {
        fos.write(byteRead);
      }
    }
  }

  // Создание тестового файла
  static void createLargeBinaryFile(String fileName, long sizeInBytes) throws IOException {
    File file = new File(fileName);
    if (file.exists() && file.length() == sizeInBytes) {
      System.out.println("Файл " + fileName + " уже существует.");
      return;
    }

    System.out.println(
        "Создание файла " + fileName + " размером " + (sizeInBytes / (1024 * 1024)) + " MB...");
    try (OutputStream fos = new FileOutputStream(fileName)) {
      byte[] chunk = new byte[1024];
      for (long i = 0; i < sizeInBytes; i += chunk.length) {
        int lengthToWrite = (int) Math.min(chunk.length, sizeInBytes - i);
        fos.write(chunk, 0, lengthToWrite);
      }
    }
    System.out.println("Файл создан.");
  }

  // Удаление файла если существует
  static void deleteFileIfExists(String fileName) {
    File file = new File(fileName);
    if (file.exists()) {
      if (file.delete()) {
        System.out.println("Удален файл: " + fileName);
      } else {
        System.err.println("Не удалось удалить файл: " + fileName);
      }
    }
  }
}