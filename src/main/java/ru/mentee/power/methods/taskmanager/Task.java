    package ru.mentee.power.methods.taskmanager;

    import java.time.LocalDate;
    import java.time.ZoneId;
    import java.util.Date;

    public class Task {
        private int id;
        private String title;
        private String description;
        private LocalDate dueDate;
        private Priority priority;
        private boolean completed;

        public enum Priority {
            LOW, MEDIUM, HIGH
        }

        public Task(int id, String title, String description, LocalDate dueDate, Priority priority) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
            this.priority = priority;
        }

        public Task(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public Task(int id, String title, String description) {
            this.id = id;
            this.title = title;
            this.description = description;
        }

        public int getId() {
            return id;
        }


        public String getTitle() {
            return title;
        }

        public void markAsCompleted() {
            completed = true;
        }

        public boolean isOverdue() {
            return dueDate.isBefore(LocalDate.now());
        }

        public Priority getPriority() {
            return priority;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getDueDate() {
            return dueDate;
        }

        public boolean isCompleted() {
            return completed;
        }

        @Override
        public String toString() {
            String status = completed ? "Выполнена" : "Не выполнена";
            String overdue = isOverdue() ? " (Просрочена)" : "";

            return String.format(
                    "Задача #%d: %s | Описание: %s | Срок: %s | Приоритет: %s | Статус: %s%s",
                    id,
                    title,
                    description != null ? description : "Нет описания",
                    dueDate != null ? dueDate.toString() : "Нет срока",
                    priority != null ? priority : "Не указан",
                    status,
                    overdue
            );
        }
    }