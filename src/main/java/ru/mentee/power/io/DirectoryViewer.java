package ru.mentee.power.io;

import java.io.File;

public class DirectoryViewer {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Ошибка: Не указан путь к директории.");
            System.err.println("Использование: java ru.mentee.power.io.DirectoryViewer <путь_к_директории>");
            return;
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Ошибка: Путь не существует или не является директорией: " + directoryPath);
            return;
        }

        System.out.println("Содержимое директории: " + directory.getAbsolutePath());
        System.out.println("--------------------------------------------------");

        File[] files = directory.listFiles();

        if (files == null) {
            System.err.println("Ошибка: Не удалось получить доступ к содержимому директории (проверьте права доступа).");
            return;
        }

        if (files.length == 0) {
            System.out.println("Директория пуста.");
            return;
        }

        for (File file : files) {
            String type = file.isDirectory() ? "[DIR]" : "[FILE]";

            String name = file.getName();

            String sizeInfo = "";
            if (file.isFile()) {
                sizeInfo = " (" + file.length() + " bytes)";
            }

            System.out.println(type + "  " + name + sizeInfo);
        }
    }
}