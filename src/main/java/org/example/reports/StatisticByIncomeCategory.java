package org.example.reports;

public class StatisticByIncomeCategory {
    private String categoryName;
    private long totalIncomeAtPennies;

    public StatisticByIncomeCategory(String categoryName, long totalIncomeAtPennies) {
        this.categoryName = categoryName;
        this.totalIncomeAtPennies = totalIncomeAtPennies;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public long getTotalIncomeAtPennies() {
        return totalIncomeAtPennies;
    }
}
