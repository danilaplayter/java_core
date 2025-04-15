package ru.mentee.power.exceptions.config;

import ru.mentee.power.exceptions.config.exception.ConfigException;
import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.HashMap;
import java.util.Map;

/**
 * Демонстрация работы ConfigManager.
 */
public class ConfigDemo {
    public static void main(String[] args) {
        Map<String, String> config = new HashMap<>();
        config.put("database.url", "jdbc:mysql://localhost:3306/mydb");
        config.put("database.user", "root");
        config.put("database.password", "secret");
        config.put("server.port", "8080");
        config.put("debug.mode", "true");
        config.put("max.connections", "100");
        config.put("timeout", "30");
        ConfigManager manager = new ConfigManager(config);

        try {

            System.out.println("=== Корректные значения ===");
            String dbUrl = manager.getRequiredValue("database.url");
            System.out.println("Database URL: " + dbUrl);

            int port = manager.getRequiredIntValue("server.port");
            System.out.println("Server port: " + port);

            boolean debugMode = manager.getRequiredBooleanValue("debug.mode");
            System.out.println("Debug mode: " + debugMode);

            // 4. Пытаемся получить несуществующий ключ
            System.out.println("\n=== Несуществующий ключ ===");
            try {
                String missingValue = manager.getRequiredValue("nonexistent.key");
            } catch (MissingConfigKeyException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }

            // 5. Пытаемся получить некорректное числовое значение
            System.out.println("\n=== Некорректное числовое значение ===");
            try {
                // Добавим некорректное значение
                manager.setValue("timeout", "thirty");
                int timeout = manager.getRequiredIntValue("timeout");
            } catch (InvalidConfigValueException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }

        } catch (ConfigException e) {
            System.err.println("Общая ошибка конфигурации: " + e.getMessage());
            e.printStackTrace();
        }

        // 6. Демонстрация с полностью невалидной конфигурацией
        System.out.println("\n=== Тест с невалидной конфигурацией ===");
        Map<String, String> invalidConfig = new HashMap<>();
        invalidConfig.put("server.port", "not_a_number");

        try {
            ConfigManager badManager = new ConfigManager(invalidConfig);
            int badPort = badManager.getRequiredIntValue("server.port");
        } catch (InvalidConfigValueException e) {
            System.err.println("Некорректное значение: " + e.getMessage());
        } catch (ConfigException e) {
            System.err.println("Другая ошибка конфигурации: " + e.getMessage());
        }
    }
}