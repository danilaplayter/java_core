package ru.mentee.power.exceptions;

public class TaskManager {

    private final String id;
    private double balance;

    public TaskManager(String id, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным: " + initialBalance);
        }
        this.id = id;
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }


    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма депозита не может быть отрицательной: " + amount);
        }
        this.balance += amount;
        System.out.printf("На счет %s внесено %.2f. Новый баланс: %.2f%n", id, amount, balance);
    }

    public void withdraw(double amount) throws TaskValidationException {
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма снятия не может быть отрицательной: " + amount);
        }
        if (amount > this.balance) {
            throw new TaskValidationException(
                    String.format("Недостаточно средств на счете %s для снятия %.2f", id, amount),
                    this.balance,
                    amount
            );
        }
        this.balance -= amount;
        System.out.printf("Со счета %s снято %.2f. Новый баланс: %.2f%n", id, amount, balance);
    }
}