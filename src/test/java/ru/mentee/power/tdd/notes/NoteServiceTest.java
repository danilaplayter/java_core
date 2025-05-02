package ru.mentee.power.tdd.notes;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Тесты для NoteService")
class NoteServiceTest {

  private NoteService noteService;

  @TempDir
  Path tempDir;
  private String testFilePath;

  @BeforeEach
  void setUp() {
    String testFilePath = tempDir.resolve("test_notes.ser").toString();
    noteService = new NoteService(testFilePath);
  }

  @Nested
  @DisplayName("Тесты добавления заметок")
  class AddNoteTests {

    @Test
    @DisplayName("Добавление валидной заметки")
    void shouldAddValidNote() {

      String title = "Первая заметка";
      String text = "Текст первой заметки";
      Set<String> tags = Set.of("java", "тест");

      Note addedNote = noteService.addNote(title, text, tags);

      assertThat(addedNote).isNotNull();
      assertThat(addedNote.getId()).isPositive();
      assertThat(title).isEqualTo(addedNote.getTitle());
      assertThat(text).isEqualTo(addedNote.getText());
      assertThat(tags).containsExactlyInAnyOrder("java", "тест");
    }

    @Test
    @DisplayName("Добавление заметки с null title/text")
    void shouldHandleNullTitleAndText() {

      Note addedNote = noteService.addNote(null, null, null);
      assertThat(addedNote.getTitle()).isNull();
      assertThat(addedNote.getText()).isNull();
      assertThat(addedNote.getTags()).isEmpty();
    }

    @Test
    @DisplayName("Добавление заметки с пустыми тегами")
    void shouldAddNoteWithEmptyTags() {
      Note addedNote = noteService.addNote("Title", "Text", Set.of());
      assertThat(addedNote.getTags()).isEmpty();
    }

    @Test
    @DisplayName("Теги должны приводиться к нижнему регистру")
    void shouldConvertTagsToLowerCase() {
      Note addedNote = noteService.addNote("Title", "Text", Set.of("JAVA", "Test"));
      assertThat(addedNote.getTags()).containsExactlyInAnyOrder("java", "test");
    }
  }

  @Nested
  @DisplayName("Тесты получения заметок")
  class GetNoteTests {

    @Test
    @DisplayName("Получение заметок по существующему ID")
    void shouldGetNoteByIdWhenExists() {
      Note addedNote = noteService.addNote("Title", "Text", Set.of("tag1"));

      Optional<Note> foundNote = noteService.getNoteById(addedNote.getId());

      assertThat(foundNote).isPresent().contains(addedNote);

    }

    @Test
    @DisplayName("Получение заметки по несуществующему ID")
    void shouldReturnEmptyWhenNoteNotFound() {
      Optional<Note> foundNote = noteService.getNoteById(999);
      assertThat(foundNote).isEmpty();
    }

    @Test
    @DisplayName("Получение всех заметок")
    void shouldGetAllNotes() {
      Note note1 = noteService.addNote("Title1", "Text1", Set.of());
      Note note2 = noteService.addNote("Title2", "Text2", Set.of());
      List<Note> allNotes = noteService.getAllNotes();
      assertThat(allNotes).containsExactlyInAnyOrder(note1, note2);
    }

    @Test
    @DisplayName("Получение всех заметок из пустого сервиса")
    void shouldGetEmptyListWhenNoNotes() {
      List<Note> allNotes = noteService.getAllNotes();
      assertThat(allNotes).isEmpty();
    }

  }

  @Nested
  @DisplayName("Тесты обновления заметок")
  class UpdateNoteTests {

    @Test
    @DisplayName("Обновление текста заметки")
    void shouldUpdateNoteText() {
      // Arrange
      Note note = noteService.addNote("Title", "Old text", Set.of());

      // Act - обновляем существующую заметку
      boolean result = noteService.updateNoteText(note.getId(), "New title", "New text");

      // Assert
      assertThat(result).isTrue(); // Проверяем успешное обновление
      Optional<Note> updatedNote = noteService.getNoteById(note.getId());
      assertThat(updatedNote).isPresent();
      assertThat(updatedNote.get().getText()).isEqualTo("New text");
      assertThat(updatedNote.get().getTitle()).isEqualTo("New title");
    }

    @Test
    @DisplayName("Обновление текста несуществующей заметки")
    void shouldNotUpdateNonExistingNote() {
      boolean result = noteService.updateNoteText(999, "New title", "New text");
      assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Добавление тега к заметке")
    void shouldAddTagToNote() {
      Note note = noteService.addNote("Title", "Text", Set.of("existing"));

      boolean result = noteService.addTagToNote(note.getId(), "newTag");

      assertThat(result).isTrue();
      Optional<Note> updatedNote = noteService.getNoteById(note.getId());
      assertThat(updatedNote).isPresent();
      assertThat(updatedNote.get().getTags()).containsExactlyInAnyOrder("existing", "newtag");
    }

    @Test
    @DisplayName("Добавление тега к несуществующей заметке")
    void shouldNotAddTagToNonExistingNote() {
      boolean result = noteService.addTagToNote(999, "tag");
      assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Удаление тега из заметки")
    void shouldRemoveTagFromNote() {
      Note note = noteService.addNote("Title", "Text", Set.of("tag1", "tag2"));

      boolean result = noteService.removeTagFromNote(note.getId(), "tag1");

      assertThat(result).isTrue();
      Optional<Note> updatedNote = noteService.getNoteById(note.getId());
      assertThat(updatedNote).isPresent();
      assertThat(updatedNote.get().getTags()).containsExactly("tag2");
    }

    @Test
    @DisplayName("Удаление несуществующего тега")
    void shouldNotRemoveNonExistingTag() {
      Note note = noteService.addNote("Title", "Text", Set.of("tag1"));
      boolean result = noteService.removeTagFromNote(note.getId(), "nonexistent");
      assertThat(result).isFalse();
    }
  }

  @Nested
  @DisplayName("Тесты удаления заметок")
  class DeleteNoteTests {

    @Test
    @DisplayName("Удаление существующей заметки")
    void shouldDeleteExistingNote() {
      // Arrange
      Note note = noteService.addNote("Title", "Text", Set.of());

      // Act
      boolean result = noteService.deleteNote(note.getId());

      // Assert
      assertThat(result).isTrue();
      assertThat(noteService.getNoteById(note.getId())).isEmpty();
    }

    @Test
    @DisplayName("Удаление несуществующей заметки")
    void shouldNotDeleteNonExistingNote() {
      // Act
      boolean result = noteService.deleteNote(999);

      // Assert
      assertThat(result).isFalse();
    }
  }

  @Nested
  @DisplayName("Тесты поиска заметок")
  class FindNoteTests {

    @Test
    @DisplayName("Поиск заметок по тексту")
    void shouldFindNotesByText() {
      // Arrange
      Note note1 = noteService.addNote("Title1", "Java is great", Set.of());
      Note note2 = noteService.addNote("Title2", "TDD is awesome", Set.of());
      noteService.addNote("Title3", "Another note", Set.of());

      // Act
      List<Note> foundNotes = noteService.findNotesByText("is");

      // Assert
      assertThat(foundNotes).containsExactlyInAnyOrder(note1, note2);
    }

    @Test
    @DisplayName("Поиск заметок по тегам")
    void shouldFindNotesByTags() {
      // Arrange
      Note note1 = noteService.addNote("Title1", "Text1", Set.of("java", "tdd"));
      Note note2 = noteService.addNote("Title2", "Text2", Set.of("tdd"));
      noteService.addNote("Title3", "Text3", Set.of("other"));

      // Act
      List<Note> foundNotes = noteService.findNotesByTags(Set.of("tdd"));

      // Assert
      assertThat(foundNotes).containsExactlyInAnyOrder(note1, note2);
    }

    @Test
    @DisplayName("Поиск по нескольким тегам")
    void shouldFindNotesByMultipleTags() {
      // Arrange
      Note note1 = noteService.addNote("Title1", "Text1", Set.of("java", "tdd"));
      noteService.addNote("Title2", "Text2", Set.of("tdd"));
      noteService.addNote("Title3", "Text3", Set.of("other"));

      // Act
      List<Note> foundNotes = noteService.findNotesByTags(Set.of("java", "tdd"));

      // Assert
      assertThat(foundNotes).containsExactly(note1);
    }
  }

  @Nested
  @DisplayName("Тесты работы с тегами")
  class TagTests {

    @Test
    @DisplayName("Получение всех тегов")
    void shouldGetAllTags() {
      // Arrange
      noteService.addNote("Title1", "Text1", Set.of("java", "tdd"));
      noteService.addNote("Title2", "Text2", Set.of("tdd", "test"));
      noteService.addNote("Title3", "Text3", Set.of());

      // Act
      Set<String> allTags = noteService.getAllTags();

      // Assert
      assertThat(allTags).containsExactlyInAnyOrder("java", "tdd", "test");
    }

    @Test
    @DisplayName("Получение тегов из пустого сервиса")
    void shouldGetEmptyTagsWhenNoNotes() {
      // Act
      Set<String> allTags = noteService.getAllTags();

      // Assert
      assertThat(allTags).isEmpty();
    }
  }

    /*@Nested
    @DisplayName("Тесты работы с файлами")
    class FileIOTests {
        @Test
        @DisplayName("Сохранение и загрузка заметок")
        void shouldSaveAndLoadNotes() throws IOException {
            // Arrange
            Note note1 = noteService.addNote("Title1", "Text1", Set.of("tag1"));
            Note note2 = noteService.addNote("Title2", "Text2", Set.of("tag2"));

            // Act - сохраняем заметки
            noteService.saveToFile();

            // Создаем новый сервис для загрузки
            NoteService loadedService = new NoteService(testFilePath);
            loadedService.loadFromFile();

            // Assert
            List<Note> loadedNotes = loadedService.getAllNotes();
            assertThat(loadedNotes).hasSize(2);
            assertThat(loadedNotes.get(0).getTitle()).isEqualTo("Title1");
            assertThat(loadedNotes.get(1).getTitle()).isEqualTo("Title2");
        }

        @Test
        @DisplayName("Загрузка из несуществующего файла")
        void shouldHandleMissingFileOnLoad() throws IOException {
            // Act
            noteService.loadFromFile(); // Файл не существует

            // Assert - не должно быть исключения
            assertThat(noteService.getAllNotes()).isEmpty();
        }
    }*/

}