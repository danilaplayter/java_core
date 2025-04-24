package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static ru.mentee.power.io.SettingsManager.loadSettings;

@DisplayName("Тесты менеджера настроек (SettingsManager)")
class SettingsManagerTest {

    @TempDir
    Path tempDir;

    private Path testFilePath;
    private List<Serializable> testSettingsList;
    private ServerConfiguration testServerConfig;
    private WindowConfiguration testWindowConfig;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_settings.ser");

        testServerConfig = new ServerConfiguration("test.server", 1234, true);
        testServerConfig.setLastStatus("Initial"); // Установим transient поле
        testWindowConfig = new WindowConfiguration("Test Window", 800, 600);

        testSettingsList = new ArrayList<>();
        testSettingsList.add(testServerConfig);
        testSettingsList.add(testWindowConfig);
    }

    @Test
    @DisplayName("Должен сохранять и загружать список с разными типами Serializable")
    void shouldSaveAndLoadListOfMixedSerializable() throws IOException, ClassNotFoundException {

        SettingsManager.saveSettings(testSettingsList, testFilePath.toString());
        List<Serializable> loadedList = loadSettings(testFilePath.toString());

        assertThat(loadedList)
                .isNotNull()
                .hasSize(2);

        assertThat(loadedList.get(0))
                .isInstanceOf(ServerConfiguration.class);

        ServerConfiguration loadedServerConfig = (ServerConfiguration) loadedList.get(0);
        assertThat(loadedServerConfig.getServerAddress()).isEqualTo(testServerConfig.getServerAddress());
        assertThat(loadedServerConfig.getServerPort()).isEqualTo(testServerConfig.getServerPort());
        assertThat(loadedServerConfig.isLoggingEnabled()).isEqualTo(testServerConfig.isLoggingEnabled());
        assertThat(loadedServerConfig.getLastStatus()).isNull(); // Transient поле не сохранилось

        assertThat(loadedList.get(1))
                .isInstanceOf(WindowConfiguration.class);

        WindowConfiguration loadedWindowConfig = (WindowConfiguration) loadedList.get(1);
        assertThat(loadedWindowConfig.windowTitle()).isEqualTo(testWindowConfig.windowTitle());
        assertThat(loadedWindowConfig.width()).isEqualTo(testWindowConfig.width());
        assertThat(loadedWindowConfig.height()).isEqualTo(testWindowConfig.height());
    }

    @Test
    @DisplayName("Должен сохранять и загружать пустой список")
    void shouldSaveAndLoadEmptyList() throws IOException, ClassNotFoundException {

        List<Serializable> emptyList = new ArrayList<>();
        SettingsManager.saveSettings(emptyList, testFilePath.toString());
        List<Serializable> loadedList = loadSettings(testFilePath.toString());
        assertThat(loadedList).isNotNull().isEmpty();

    }


    @Test
    @DisplayName("loadSettings должен возвращать пустой список, если файл не найден")
    void loadShouldReturnEmptyListWhenFileNotExists() {

        assertThat(testFilePath).doesNotExist();

        List<Serializable> loadedList = loadSettings(testFilePath.toString());
        assertThat(loadedList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("loadSettings должен возвращать пустой список при ошибке десериализации")
    void loadShouldReturnEmptyListOnDeserializationError() throws IOException {

        Files.writeString(testFilePath, "Это не сериализованный список");

        List<Serializable> loadedList = SettingsManager.loadSettings(testFilePath.toString());

        assertThat(loadedList).isNotNull().isEmpty();

    }

}
