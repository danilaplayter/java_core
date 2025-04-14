package ru.mentee.power.methods.taskmanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;
    private int nextId = 1;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public Task addTask(String title, String description, LocalDate dueDate, Task.Priority priority) {
        Task task =  new Task(nextId++, title, description, dueDate, priority);
        tasks.add(task);
        return task;
    }

    public Task addTask(String title) {
        Task task =  new Task(nextId++, title);
        tasks.add(task);
        return task;
    }

    public Task addTask(String title, String description) {
        Task task =  new Task(nextId++, title, description);
        tasks.add(task);
        return task;
    }

    public Task getTaskById(int id) {
        for(Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public boolean removeTask(int id) {
        for(Task task : tasks){
            if(task.getId() == id){
                tasks.remove(task);
                return true;
            }
        }
        return false;
    }

    public boolean markTaskAsCompleted(int id) {
        Task task = getTaskById(id);
        if(task != null){
            task.markAsCompleted();
            return true;
        }
        return false;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public List<Task> getCompletedTasks() {
        List<Task> completedTasks = new ArrayList<>();
        for(Task task : tasks){
            if(task.isCompleted()){
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    public List<Task> getIncompleteTasks() {

        List<Task> incompletedTasks = new ArrayList<>();
        for(Task task : tasks){
            if(!task.isCompleted()){
                incompletedTasks.add(task);
            }
        }
        return incompletedTasks;
    }

    public List<Task> getOverdueTasks() {

        List<Task> overdueTasks = new ArrayList<>();
        for(Task task : tasks){
            if(task.isOverdue()){
                overdueTasks.add(task);
            }
        }
        return overdueTasks;
    }

    public List<Task> getTasksByPriority(Task.Priority priority) {

        List<Task> tasksByPriority = new ArrayList<>();
        for (Task task : tasks){
            if(task.getPriority() == priority){
                tasksByPriority.add(task);
            }
        }
        return tasksByPriority;
    }

    public List<Task> searchTasks(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }
        List<Task> queryTasks = new ArrayList<>();
        for(Task task : tasks) {
            if ((task.getTitle() != null && task.getTitle().contains(query)) ||
                    (task.getDescription() != null && task.getDescription().contains(query))){
                queryTasks.add(task);
            }
        }

        return queryTasks;
    }

    public List<Task> sortTasksByDueDate() {
        List<Task> sortedTasks = new ArrayList<>(tasks);

        for (int i = 0; i < sortedTasks.size() - 1; i++) {
            for (int j = 0; j < sortedTasks.size() - i - 1; j++) {
                LocalDate date1 = sortedTasks.get(j).getDueDate();
                LocalDate date2 = sortedTasks.get(j + 1).getDueDate();

                // Обработка null значений
                if (date1 == null && date2 == null) continue;
                if (date1 == null) {
                    Collections.swap(sortedTasks, j, j + 1);
                    continue;
                }
                if (date2 == null) continue;

                // Сравнение дат
                if (date1.isAfter(date2)) {
                    Collections.swap(sortedTasks, j, j + 1);
                }
            }
        }

        return sortedTasks;
    }

    public List<Task> sortTasksByPriority() {
        List<Task> sortedTasks = new ArrayList<>(tasks);

        for (int i = 1; i < sortedTasks.size(); i++) {
            Task current = sortedTasks.get(i);
            int j = i - 1;

            while (j >= 0 && comparePriorities(sortedTasks.get(j).getPriority(), current.getPriority()) > 0) {
                sortedTasks.set(j + 1, sortedTasks.get(j));
                j--;
            }

            sortedTasks.set(j + 1, current);
        }

        return sortedTasks;
    }

    private int comparePriorities(Task.Priority p1, Task.Priority p2) {
        if (p1 == null && p2 == null) return 0;
        if (p1 == null) return 1;
        if (p2 == null) return -1;
        return p2.ordinal() - p1.ordinal();
    }

    public void printAllTasks() {
        for(Task task : tasks){
            System.out.println(task.toString());
        }
    }

    public void printTasks(List<Task> taskList, String header) {
        if (header != null && !header.isEmpty()) {
            System.out.println("=== " + header + " ===");
        }

        if (taskList == null || taskList.isEmpty()) {
            System.out.println("Нет задач для отображения");
            return;
        }
        for (Task task : taskList) {
            System.out.println(task.toString());
        }
        System.out.println();
    }
}