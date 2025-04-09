package ru.mentee.power.methods.library;

public class LibraryDemo {
    public static void main(String[] args) {
        Library library = new Library(10); // Создаём библиотеку с вместимостью 10 книг

        library.addBook(new Book("В стальных грозах", "Эрнст Юнгер", 1920));
        library.addBook(new Book("Триумфальная арка", "Эрих Мария Ремарк", 1921));
        library.addBook(new Book("Война и мир", "Лев Толстой", 1882));
        library.addBook(new Book("Преступление и наказание", "Фёдор Достоевский", 1889));

        System.out.println("Доступные книги:");
        for (Book book : library.listAvailableBooks()) {
            System.out.println(book.getTitle());
        }

        library.checkoutBook("Война и мир");

        System.out.println("\nПосле выдачи 'Война и мир':");
        System.out.println("Доступные книги:");
        for (Book book : library.listAvailableBooks()) {
            System.out.println(book.getTitle());
        }

        System.out.println("\nВыданные книги:");
        for (Book book : library.listCheckedOutBooks()) {
            System.out.println(book.getTitle());
        }

        library.returnBook("Война и мир");

        System.out.println("\nПосле возврата 'Война и мир':");
        System.out.println("Доступные книги:");
        for (Book book : library.listAvailableBooks()) {
            System.out.println(book.getTitle());
        }

        System.out.println("\nВыданные книги:");
        for (Book book : library.listCheckedOutBooks()) {
            System.out.println(book.getTitle());
        }
    }
}