package ru.mentee.power.tdd.notes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class NoteService {

  private final Map<Integer, Note> notes = new HashMap<>();
  private final AtomicInteger nextId = new AtomicInteger(1);
  private final String storageFilePath;

  public NoteService() {
    this(null);
  }

  public NoteService(String storageFilePath) {
    this.storageFilePath = storageFilePath;
    if (storageFilePath != null) {
      loadFromFile();
    }
  }

  public Note addNote(String title, String text, Set<String> tags) {

    int id = nextId.getAndIncrement();
    Note note = new Note(id, title, text);

    if (tags != null) {
      tags.forEach(note::addTag);
    }

    notes.put(id, note);
    return note;
  }

  public Optional<Note> getNoteById(int id) {
    return Optional.ofNullable(notes.get(id));
  }

  public List<Note> getAllNotes() {
    return List.copyOf(notes.values());
  }

  public boolean updateNoteText(int id, String newTitle, String newText) {

    return getNoteById(id).map(note -> {
      note.setTitle(newTitle);
      note.setText(newText);
      return true;
    }).orElse(false);
  }

  public boolean addTagToNote(int id, String tag) {
    if (tag == null || tag.trim().isEmpty()) {
      return false;
    }

    return getNoteById(id).map(note -> {
      note.addTag(tag);
      return true;
    }).orElse(false);
  }

  public boolean removeTagFromNote(int id, String tag) {
    if (tag == null) {
      return false;
    }

    return getNoteById(id).map(note -> note.removeTag(tag)).orElse(false);
  }

  public boolean deleteNote(int id) {
    return notes.remove(id) != null;
  }

  public List<Note> findNotesByText(String query) {
    if (query == null || query.trim().isEmpty()) {
      return List.of();
    }

    String lowerQuery = query.toLowerCase();
    return notes.values().stream().filter(
        note -> note.getTitle().toLowerCase().contains(lowerQuery) || note.getText().toLowerCase()
            .contains(lowerQuery)).collect(Collectors.toList());
  }

  public List<Note> findNotesByTags(Set<String> searchTags) {
    if (searchTags == null || searchTags.isEmpty()) {
      return List.of();
    }

    Set<String> normalizedTags = searchTags.stream().map(String::toLowerCase)
        .collect(Collectors.toSet());

    return notes.values().stream().filter(note -> note.getTags().containsAll(normalizedTags))
        .collect(Collectors.toList());
  }

  public Set<String> getAllTags() {
    return notes.values().stream().flatMap(note -> note.getTags().stream())
        .collect(Collectors.toSet());
  }

  public void saveToFile() throws NoteServiceException {
    // Реализация сохранения в файл (CSV или сериализация)
    // Обработка IOException и преобразование в NoteServiceException
  }

  private void loadFromFile() throws NoteServiceException {
    // Реализация загрузки из файла
    // Обработка ошибок и установка nextId
  }
}