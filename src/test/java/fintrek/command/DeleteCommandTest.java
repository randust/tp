package fintrek.command;

import org.junit.jupiter.api.BeforeEach;

import fintrek.ExpenseManager;

public class DeleteCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearExpenses();
    }
}
