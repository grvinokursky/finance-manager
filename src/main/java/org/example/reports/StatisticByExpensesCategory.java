package org.example.reports;

public class StatisticByExpensesCategory {
    private String categoryName;
    private int totalExpenses;
    private int limit;

    public StatisticByExpensesCategory(String categoryName, int totalExpenses, int limit) {
        this.categoryName = categoryName;
        this.totalExpenses = totalExpenses;
        this.limit = limit;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public int getLimit() {
        return limit;
    }
}
