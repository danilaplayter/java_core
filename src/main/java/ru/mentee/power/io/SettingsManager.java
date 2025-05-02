package ru.mentee.power.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class ServerConfiguration implements Serializable {

  @Serial
  private static final long serialVersionUID = 301L;

  private String serverAddress;
  private int serverPort;
  private boolean loggingEnabled;
  private transient String lastStatus = "Idle";

  public ServerConfiguration(String serverAddress, int serverPort, boolean loggingEnabled) {
    this.serverAddress = serverAddress;
    this.serverPort = serverPort;
    this.loggingEnabled = loggingEnabled;
  }

  public String getServerAddress() {
    return serverAddress;
  }

  public int getServerPort() {
    return serverPort;
  }

  public boolean isLoggingEnabled() {
    return loggingEnabled;
  }

  public String getLastStatus() {
    return lastStatus;
  }

  public void setLastStatus(String lastStatus) {
    this.lastStatus = lastStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerConfiguration that = (ServerConfiguration) o;
    return serverPort == that.serverPort && loggingEnabled == that.loggingEnabled && Objects.equals(
        serverAddress, that.serverAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serverAddress, serverPort, loggingEnabled);
  }

  @Override
  public String toString() {
    return "ServerConfiguration{" + "server='" + serverAddress + ':' + serverPort + ", logging="
        + loggingEnabled + ", status='" + lastStatus + '\'' + '}';
  }
}

record WindowConfiguration(String windowTitle, int width, int height) implements Serializable {

  @Serial
  private static final long serialVersionUID = 302L;
}

public class SettingsManager {

  public static void saveSettings(List<Serializable> settings, String filename) {
    try (ObjectOutputStream oos = new ObjectOutputStream(
        new BufferedOutputStream(new FileOutputStream(filename)))) {
      oos.writeObject(settings);
      System.out.println("Список настроек сохранен в " + filename);
    } catch (IOException e) {
      System.out.println("Ошибка при сохранении настроек: " + e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  public static List<Serializable> loadSettings(String filename) {
    List<Serializable> loadedSettings = new ArrayList<>();
    File file = new File(filename);

    if (!file.exists()) {
      System.out.println("Файл настроек " + filename + " не найден.");
      return loadedSettings;
    }

    try (FileInputStream fis = new FileInputStream(
        file); BufferedInputStream bis = new BufferedInputStream(
        fis); ObjectInputStream ois = new ObjectInputStream(bis)) {

      Object obj = ois.readObject();
      if (obj instanceof List) {
        loadedSettings = (List<Serializable>) obj;
      }
      System.out.println("Список настроек загружен из " + filename);
    } catch (ClassCastException e) {
      System.err.println("Ошибка формата файла: " + e.getMessage());
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Ошибка при загрузке: " + e.getMessage());
    }

    return loadedSettings;
  }

  public static void main(String[] args) {
    String filename = "settings.ser";

    List<Serializable> currentSettings = new ArrayList<>();
    currentSettings.add(new ServerConfiguration("prod.server.com", 443, true));
    currentSettings.add(new WindowConfiguration("Основное Окно Приложения", 1280, 720));

    if (!currentSettings.isEmpty() && currentSettings.get(0) instanceof ServerConfiguration) {
      ((ServerConfiguration) currentSettings.get(0)).setLastStatus("Перед сохранением");
    }
    System.out.println("Сохраняем список: " + currentSettings);

    saveSettings(currentSettings, filename);

    System.out.println("\nЗагружаем список...");
    List<Serializable> loadedSettings = loadSettings(filename);

    if (loadedSettings.isEmpty()) {
      System.out.println("Не удалось загрузить настройки.");
    } else {
      System.out.println("Загруженный список: " + loadedSettings);
      System.out.println("--- Проверка объектов в списке ---");
      for (Serializable setting : loadedSettings) {
        if (setting instanceof ServerConfiguration configClass) {
          System.out.println(
              "  Загружен Server Config: server=" + configClass.getServerAddress() + ", status="
                  + configClass.getLastStatus());
        } else if (setting instanceof WindowConfiguration configRecord) {
          System.out.println(
              "  Загружен Window Config: title=" + configRecord.windowTitle() + ", size="
                  + configRecord.width() + "x" + configRecord.height());
        } else {
          System.out.println("  Загружен неизвестный тип: " + setting.getClass());
        }
      }
    }
  }
}