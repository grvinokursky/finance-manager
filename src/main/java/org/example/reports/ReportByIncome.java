package org.example.reports;

import java.util.List;

public class ReportByIncome {
    private long totalIncomeAtPennies;
    private List<StatisticByIncomeCategory> statisticsByIncomeCategories;

    public long getTotalIncomeAtPennies() {
        return totalIncomeAtPennies;
    }

    public void setTotalIncomeAtPennies(long totalIncomeAtPennies) {
        this.totalIncomeAtPennies = totalIncomeAtPennies;
    }

    public List<StatisticByIncomeCategory> getStatisticsByIncomeCategories() {
        return statisticsByIncomeCategories;
    }

    public void setStatisticsByIncomeCategories(List<StatisticByIncomeCategory> statisticsByIncomeCategories) {
        this.statisticsByIncomeCategories = statisticsByIncomeCategories;
    }
}
