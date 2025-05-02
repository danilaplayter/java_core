package ru.mentee.power.methods.taskmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskManagerTest {

  private TaskManager taskManager;
  private Task highPriorityTask;
  private Task mediumPriorityTask;
  private Task lowPriorityTask;
  private Task noDescriptionTask;
  private Task noDueDateTask;

  @BeforeEach
  void setUp() {
    taskManager = new TaskManager();

    highPriorityTask = taskManager.addTask("Срочная задача", "Выполнить скорее",
        LocalDate.of(2023, 5, 15), Task.Priority.HIGH);

    mediumPriorityTask = taskManager.addTask("Обычная задача", "В течение недели",
        LocalDate.of(2023, 6, 1), Task.Priority.MEDIUM);

    lowPriorityTask = taskManager.addTask("Несрочная задача", "Когда будет время",
        LocalDate.of(2023, 7, 1), Task.Priority.LOW);

    noDescriptionTask = taskManager.addTask("Задача без описания");

    noDueDateTask = taskManager.addTask("Задача без срока", "Описание", null, null);
  }

  @Test
  void addTask() {
    Task newTask = taskManager.addTask("Новая задача", "Описание",
        LocalDate.of(2023, 8, 1), Task.Priority.MEDIUM);

    assertThat(newTask)
        .hasFieldOrPropertyWithValue("title", "Новая задача")
        .hasFieldOrPropertyWithValue("description", "Описание")
        .hasFieldOrPropertyWithValue("dueDate", LocalDate.of(2023, 8, 1))
        .hasFieldOrPropertyWithValue("priority", Task.Priority.MEDIUM);
    Task task = taskManager.addTask("Минимальная задача");

    assertThat(task)
        .hasFieldOrPropertyWithValue("title", "Минимальная задача")
        .hasFieldOrPropertyWithValue("description", null)
        .hasFieldOrPropertyWithValue("dueDate", null)
        .hasFieldOrPropertyWithValue("priority", null);
  }

  @Test
  void getTaskById() {
    Task foundTask = taskManager.getTaskById(highPriorityTask.getId());

    assertThat(foundTask).isEqualTo(highPriorityTask);
  }

  @Test
  void getTasksByPriority() {
    List<Task> highPriorityTasks = taskManager.getTasksByPriority(Task.Priority.HIGH);

    assertThat(highPriorityTasks)
        .hasSize(1)
        .containsExactly(highPriorityTask);
  }

  @Test
  void searchTasks() {
    List<Task> foundTasks = taskManager.searchTasks("");
    assertThat(foundTasks).isEmpty();
  }

  @Test
  void testGetTasksByPriority() {
    List<Task> highPriorityTasks = taskManager.getTasksByPriority(Task.Priority.HIGH);
    assertThat(highPriorityTasks)
        .hasSize(1)
        .containsExactly(highPriorityTask);

    List<Task> mediumPriorityTasks = taskManager.getTasksByPriority(Task.Priority.MEDIUM);
    assertThat(mediumPriorityTasks)
        .hasSize(1)
        .containsExactly(mediumPriorityTask);

    List<Task> lowPriorityTasks = taskManager.getTasksByPriority(Task.Priority.LOW);
    assertThat(lowPriorityTasks)
        .hasSize(1)
        .containsExactly(lowPriorityTask);

    List<Task> nullPriorityTasks = taskManager.getTasksByPriority(null);
    assertThat(nullPriorityTasks)
        .hasSize(2) // noDescriptionTask and noDueDateTask have null priority
        .containsExactlyInAnyOrder(noDescriptionTask, noDueDateTask);
  }

  @Test
  void testSearchTasks() {
    List<Task> foundByTitle = taskManager.searchTasks("Срочная");
    assertThat(foundByTitle)
        .hasSize(1)
        .containsExactly(highPriorityTask);

    List<Task> foundByDescription = taskManager.searchTasks("недели");
    assertThat(foundByDescription)
        .hasSize(1)
        .containsExactly(mediumPriorityTask);

    List<Task> foundNoMatch = taskManager.searchTasks("несуществующий текст");
    assertThat(foundNoMatch).isEmpty();

    List<Task> foundEmptyQuery = taskManager.searchTasks("");
    assertThat(foundEmptyQuery).isEmpty();

    List<Task> foundNullQuery = taskManager.searchTasks(null);
    assertThat(foundNullQuery).isEmpty();
  }

  @Test
  void testSortTasksByPriority() {
    Task anotherHighPriority = taskManager.addTask("Еще срочная", null, null, Task.Priority.HIGH);
    Task anotherMediumPriority = taskManager.addTask("Еще обычная", null, null,
        Task.Priority.MEDIUM);

    List<Task> sortedTasks = taskManager.sortTasksByPriority();

    assertThat(sortedTasks)
        .hasSize(7)
        .startsWith(highPriorityTask, anotherHighPriority)
        .containsSequence(mediumPriorityTask, anotherMediumPriority)
        .contains(lowPriorityTask)
        .endsWith(noDescriptionTask, noDueDateTask);

    assertThat(sortedTasks.indexOf(highPriorityTask)).isLessThan(
        sortedTasks.indexOf(anotherHighPriority));
    assertThat(sortedTasks.indexOf(mediumPriorityTask)).isLessThan(
        sortedTasks.indexOf(anotherMediumPriority));

    assertThat(sortedTasks.get(sortedTasks.size() - 2)).isEqualTo(noDescriptionTask);
    assertThat(sortedTasks.get(sortedTasks.size() - 1)).isEqualTo(noDueDateTask);
  }


}