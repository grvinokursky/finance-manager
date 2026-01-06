package org.example.reports;

import java.util.List;

public class ReportByIncome {
    private int totalIncome;
    private List<StatisticByIncomeCategory> statisticsByIncomeCategories;

    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public List<StatisticByIncomeCategory> getStatisticsByIncomeCategories() {
        return statisticsByIncomeCategories;
    }

    public void setStatisticsByIncomeCategories(List<StatisticByIncomeCategory> statisticsByIncomeCategories) {
        this.statisticsByIncomeCategories = statisticsByIncomeCategories;
    }
}
