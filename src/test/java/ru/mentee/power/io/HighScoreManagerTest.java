package ru.mentee.power.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты менеджера данных HighScoreManager")
class HighScoreManagerTest {

    @TempDir
    Path tempDir;

    private Path testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_scores.dat");
    }

    @Test
    @DisplayName("Должен сохранять и загружать пустой список")
    void shouldSaveAndLoadEmptyList() throws IOException {

        List<HighScoreEntryClass> emptyList = new ArrayList<>();
        HighScoreManager.saveScores(emptyList, testFilePath.toString());
        List<HighScoreEntryClass> loadedList = HighScoreManager.loadScores(testFilePath.toString());
        assertThat(loadedList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Должен сохранять и загружать список с несколькими записями")
    void shouldSaveAndLoadPopulatedList() throws IOException {
        List<HighScoreEntryClass> testScores = List.of(
                new HighScoreEntryClass("Alice", 1000),
                new HighScoreEntryClass("Bob", 800),
                new HighScoreEntryClass("Charlie", 1200)
        );
        HighScoreManager.saveScores(testScores, testFilePath.toString());

        List<HighScoreEntryClass> loadedScores = HighScoreManager.loadScores(testFilePath.toString());

        assertThat(loadedScores).hasSize(testScores.size());

        for(int i =0; i < testScores.size(); i++){
            HighScoreEntryClass original = testScores.get(i);
            HighScoreEntryClass loaded = loadedScores.get(i);

            assertThat(loaded.getPlayerName()).isEqualTo(original.getPlayerName());
            assertThat(loaded.getScore()).isEqualTo(original.getScore());
        }
    }

    @Test
    @DisplayName("Должен возвращать пустой список при загрузке несуществующего файла")
    void shouldReturnEmptyListForNonExistentFile() {

        Path nonExistentFile = tempDir.resolve("non_existent.dat");

        List<HighScoreEntryClass> loadedScores = HighScoreManager.loadScores(nonExistentFile.toString());

        assertThat(loadedScores).isNotNull().isEmpty();
    }
}