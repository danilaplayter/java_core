package ru.mentee.power.tdd.notes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Note implements Serializable {

  private static final long serialVersionUID = 1L;

  private final int id; // Уникальный ID
  private String title; // Заголовок
  private String text; // Текст заметки
  private final LocalDate creationDate; // Дата создания
  private Set<String> tags; // Набор тегов (уникальные строки)

  public Note(int id, String title, String text) {
    this.id = id;
    this.title = title;
    this.text = text;
    this.creationDate = LocalDate.now();
    this.tags = new HashSet<>();
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getText() {
    return text;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public Set<String> getTags() {
    return Collections.unmodifiableSet(tags);
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void addTag(String tag) {
    tags.add(tag.toLowerCase());
  }

  public boolean removeTag(String tag) {
    return tags.remove(tag.toLowerCase());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Note note = (Note) o;
    return id == note.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String toCsvString(String delimiter) {
    return String.join(delimiter, String.valueOf(id), title, text, creationDate.toString(),
        String.join(",", tags));
  }

  public static Note fromCsvString(String line, String delimiter) {
    String[] parts = line.split(delimiter);
    if (parts.length < 4) {
      throw new IllegalArgumentException("Invalid CSV format");
    }

    int id = Integer.parseInt(parts[0]);
    String title = parts[1];
    String text = parts[2];
    LocalDate creationDate = LocalDate.parse(parts[3]);

    Note note = new Note(id, title, text);
    if (parts.length > 4) {
      String[] tagArray = parts[4].split(",");
      for (String tag : tagArray) {
        if (!tag.isEmpty()) {
          note.addTag(tag);
        }
      }
    }
    return note;
  }
}