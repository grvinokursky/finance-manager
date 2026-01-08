package org.example.reports;

public class StatisticByExpensesCategory {
    private String categoryName;
    private long totalExpensesAtPennies;
    private long limitAtPennies;

    public StatisticByExpensesCategory(String categoryName, long totalExpensesAtPennies, long limitAtPennies) {
        this.categoryName = categoryName;
        this.totalExpensesAtPennies = totalExpensesAtPennies;
        this.limitAtPennies = limitAtPennies;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public long getTotalExpensesAtPennies() {
        return totalExpensesAtPennies;
    }

    public long getLimitAtPennies() {
        return limitAtPennies;
    }
}
