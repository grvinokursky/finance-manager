package org.example.reports;

public class StatisticByIncomeCategory {
    private String categoryName;
    private int totalIncome;

    public StatisticByIncomeCategory(String categoryName, int totalIncome) {
        this.categoryName = categoryName;
        this.totalIncome = totalIncome;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getTotalIncome() {
        return totalIncome;
    }
}
