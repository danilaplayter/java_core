package ru.mentee.power.exceptions.config.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mentee.power.exceptions.TaskManager;
import ru.mentee.power.exceptions.TaskValidationException;

import static org.assertj.core.api.Assertions.*;


class TaskManagerTest {

    private TaskManager account;
    private static final double INITIAL_BALANCE = 1000.0;
    private static final String ACCOUNT_ID = "ACC-123";

    @BeforeEach
    void setUp() {
        account = new TaskManager(ACCOUNT_ID, INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Конструктор должен правильно устанавливать начальный баланс и ID")
    void constructorShouldSetInitialBalanceAndId() {
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE);
        assertThat(account.getId()).isEqualTo(ACCOUNT_ID);
    }

    @Test
    @DisplayName("Конструктор должен выбрасывать IllegalArgumentException при отрицательном балансе")
    void constructorShouldThrowIllegalArgumentExceptionForNegativeBalance() {
        assertThatThrownBy(() -> new TaskManager(ACCOUNT_ID, -123)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Начальный баланс не может быть отрицательным");
    }

    // --- Тесты для deposit ---
    @Test
    @DisplayName("Метод deposit должен увеличивать баланс при положительной сумме")
    void depositShouldIncreaseBalanceForPositiveAmount() {
        double initialBalance = account.getBalance();
        double depositAmount = 100.0;
        account.deposit(depositAmount);
        assertThat(account.getBalance()).isEqualTo(initialBalance + depositAmount);

    }

    @Test
    @DisplayName("Метод deposit должен допускать нулевую сумму")
    void depositShouldAllowZeroAmount() {
        double initialBalance = account.getBalance();
        double depositAmount = 0.0;
        account.deposit(depositAmount);
        assertThat(account.getBalance()).isEqualTo(initialBalance);
    }

    @Test
    @DisplayName("Метод deposit должен выбрасывать IllegalArgumentException при отрицательной сумме")
    void depositShouldThrowIllegalArgumentExceptionForNegativeAmount() {
        double initialBalance = account.getBalance();
        assertThatThrownBy(() -> account.deposit(-100)).isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Сумма депозита не может быть отрицательной");
        assertThat(account.getBalance()).isEqualTo(initialBalance);
    }

    // --- Тесты для withdraw ---
    @Test
    @DisplayName("Метод withdraw должен уменьшать баланс при корректной сумме")
    void withdrawShouldDecreaseBalanceForValidAmount() throws TaskValidationException {
        double initialBalance = account.getBalance();
        double  withdrawAmount = 100.0;
        account.withdraw(withdrawAmount);
        assertThat(account.getBalance()).isEqualTo(initialBalance-withdrawAmount);
    }

    @Test
    @DisplayName("Метод withdraw должен позволять снять полный баланс")
    void withdrawShouldAllowWithdrawingFullBalance() throws TaskValidationException {
        double initialBalance = account.getBalance();
        account.withdraw(INITIAL_BALANCE);
        assertThat(account.getBalance()).isEqualTo(0);

    }

    @Test
    @DisplayName("Метод withdraw должен допускать нулевую сумму")
    void withdrawShouldAllowZeroAmount() throws TaskValidationException {
        double initialBalance = account.getBalance();
        account.withdraw(0);
        assertThat(account.getBalance()).isEqualTo(initialBalance);
    }

    @Test
    @DisplayName("Метод withdraw должен выбрасывать IllegalArgumentException при отрицательной сумме")
    void withdrawShouldThrowIllegalArgumentExceptionForNegativeAmount() {
        assertThatThrownBy(() -> account.withdraw(-100)).isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Сумма снятия не может быть отрицательной");
    }

    @Test
    @DisplayName("Метод withdraw должен выбрасывать TaskValidationException при превышении баланса")
    void withdrawShouldThrowTaskValidationExceptionWhenAmountExceedsBalance() {
        double initialBalance = account.getBalance();
        double excessiveAmount = INITIAL_BALANCE + 100.0;

        assertThatThrownBy(() -> account.withdraw(excessiveAmount))
                .isInstanceOf(TaskValidationException.class)
                .hasMessageContaining("Недостаточно средств на счете")
                .satisfies(e -> {
                    TaskValidationException ex = (TaskValidationException) e;
                    assertThat(ex.getBalance()).isEqualTo(INITIAL_BALANCE);
                    assertThat(ex.getWithdrawAmount()).isEqualTo(excessiveAmount);
                    assertThat(ex.getDeficit()).isEqualTo(100.0);
                });

        assertThat(account.getBalance()).isEqualTo(initialBalance);
    }
}