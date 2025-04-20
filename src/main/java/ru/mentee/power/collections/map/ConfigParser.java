package ru.mentee.power.collections.map;

import java.util.*;
import java.util.stream.Collectors;

public class ConfigParser {

    private Map<String, String> configMap;

    public ConfigParser() {
        configMap = new HashMap<>();
    }

    public void parseConfig(String configString) {

        if (configString == null) {
            throw new IllegalArgumentException("Строка конфигурации - null");
        }

        String[] lines = configString.split("\\R");

        for (String line : lines) {
            String trimmedLine = line.trim();

            if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                continue;
            }

            String[] parts = trimmedLine.split("=", 2);

            if (parts.length != 2) {
                continue;
            }

            String key = parts[0].trim();
            String value = parts[1].trim();
            configMap.put(key, value);

        }

    }

    public String toConfigString() {

        return configMap.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).
                collect(Collectors.joining("\n"));

    }

    public String getValue(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ = null");
        }
        return configMap.get(key);

    }

    public String getValue(String key, String defaultValue) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ = null");
        }

        return configMap.getOrDefault(key, defaultValue);
    }

    public String setValue(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ = null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Значение = null");
        }
        return configMap.put(key, value);

    }

    public boolean removeKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ = null");
        }

        return configMap.remove(key) != null;

    }

    public boolean containsKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ = null");
        }

        return configMap.containsKey(key);

    }

    public Set<String> getKeys() {
        return configMap.keySet();
    }

    public Map<String, String> getAll() {
        return new HashMap<>(configMap);
    }

    public int getIntValue(String key) {
        String value = getValue(key);

        if(value == null){
            throw new NoSuchElementException("Ключ '" + key + "' не найден");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Значение '" + value + "' не является числом");
        }

    }

    public boolean getBooleanValue(String key) {
        String value = getValue(key);

        if(value == null){
            throw new NoSuchElementException("Ключ '" + key + "' не найден");
        }

        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("1");

    }

    public List<String> getListValue(String key) {
        String value = getValue(key);
        if (value == null) {
            return Collections.emptyList();  // Пустой список, если ключа нет
        }
        return Arrays.asList(value.split("\\s*,\\s*"));
    }

    public void clear() {
        configMap.clear();
    }

    public int size() {
        return configMap.size();
    }
}