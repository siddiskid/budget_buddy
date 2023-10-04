package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// The Budget class represents basic information about a monthly budget.
// It has fields for the monthly budget limit and the month and year in which the budget was set.
public class Budget {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");

    private int monthlyBudget;
    private final String monthYear;

    // EFFECTS: initializes a Budget Object with 0 monthly budget and the current month as the month
    //          whose budget it is keeping track of
    public Budget(int monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
        this.monthYear = dtf.format(LocalDateTime.now());
    }

    // REQUIRES: monthlyBudget > 0
    // MODIFIES: this
    public void setBudget(int monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    // EFFECTS: returns true if total cost of all transactions exceeds the monthly budget,
    //          false otherwise.
    public Boolean isOverBudget(TransactionList transactionList) {
        return transactionList.getMonthlyTotal(this.monthYear) > this.monthlyBudget;
    }

    // EFFECTS: returns money left in the monthly budget
    public int getMoneyLeft(TransactionList transactionList) {
        if (isOverBudget(transactionList)) {
            return 0;
        }

        int totalToSubtract = 0;

        for (int i = 0; i < transactionList.getSize(); i++) {
            if (transactionList.getTransaction(i).getDate().substring(3).equals(this.monthYear)) {
                totalToSubtract += transactionList.getTransaction(i).getCost();
            }
        }
        return monthlyBudget - totalToSubtract;
    }

    // EFFECTS: returns the monthly budget
    public int getMonthlyBudget() {
        return this.monthlyBudget;
    }

    // EFFECTS: returns the month and year in which the budget was set
    public String getMonthYear() {
        return this.monthYear;
    }
}
