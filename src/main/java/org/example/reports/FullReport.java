package org.example.reports;

import java.util.List;

public class FullReport {
    private long totalIncomeAtPennies;
    private List<StatisticByIncomeCategory> statisticsByIncomeCategories;
    private long totalExpensesAtPennies;
    private List<StatisticByExpensesCategory> statisticsByExpensesCategories;

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

    public long getTotalExpensesAtPennies() {
        return totalExpensesAtPennies;
    }

    public void setTotalExpensesAtPennies(long totalExpensesAtPennies) {
        this.totalExpensesAtPennies = totalExpensesAtPennies;
    }

    public List<StatisticByExpensesCategory> getStatisticsByExpensesCategories() {
        return statisticsByExpensesCategories;
    }

    public void setStatisticsByExpensesCategories(List<StatisticByExpensesCategory> statisticsByExpensesCategories) {
        this.statisticsByExpensesCategories = statisticsByExpensesCategories;
    }
}
