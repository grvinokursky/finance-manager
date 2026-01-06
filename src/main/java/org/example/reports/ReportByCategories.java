package org.example.reports;

import java.util.List;

public class ReportByCategories {
    private List<StatisticByIncomeCategory> statisticsByIncomeCategories;
    private List<StatisticByExpensesCategory> statisticsByExpensesCategories;

    public List<StatisticByIncomeCategory> getStatisticsByIncomeCategories() {
        return statisticsByIncomeCategories;
    }

    public void setStatisticsByIncomeCategories(List<StatisticByIncomeCategory> statisticsByIncomeCategories) {
        this.statisticsByIncomeCategories = statisticsByIncomeCategories;
    }

    public List<StatisticByExpensesCategory> getStatisticsByExpensesCategories() {
        return statisticsByExpensesCategories;
    }

    public void setStatisticsByExpensesCategories(List<StatisticByExpensesCategory> statisticsByExpensesCategories) {
        this.statisticsByExpensesCategories = statisticsByExpensesCategories;
    }
}
