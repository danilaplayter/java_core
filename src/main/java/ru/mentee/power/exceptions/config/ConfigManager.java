package ru.mentee.power.exceptions.config;

import ru.mentee.power.exceptions.config.exception.MissingConfigKeyException;
import ru.mentee.power.exceptions.config.exception.InvalidConfigValueException;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final Map<String, String> config;

    public ConfigManager(Map<String, String> config) {
       if(config == null){
           throw new IllegalArgumentException("Конфигурационная карта не может быть null");
       }
       this.config = new HashMap<>(config);
    }

    public ConfigManager() {
        this.config = new HashMap<>();
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public String getRequiredValue(String key) throws MissingConfigKeyException {
        if(!config.containsKey(key)){
            throw new MissingConfigKeyException(key);
        }
        return config.get(key);
    }

    public int getRequiredIntValue(String key)
            throws MissingConfigKeyException, InvalidConfigValueException {
        String value = getRequiredValue(key);
       try{
           return Integer.parseInt(value);
       }catch (NumberFormatException e){
           throw new InvalidConfigValueException(key, value);
       }
    }

    public boolean getRequiredBooleanValue(String key)
            throws MissingConfigKeyException, InvalidConfigValueException {
        String value = getRequiredValue(key);
        if("true".equalsIgnoreCase(value)){
            return true;
        }
        if("false".equalsIgnoreCase(value)){
            return false;
        }
        throw new InvalidConfigValueException(key, value);
    }

    public void setValue(String key, String value) {
        if(key == null || value == null){
            throw new IllegalArgumentException("Ключ и значение не могут быть null");
        }
        config.put(key, value);
    }
}