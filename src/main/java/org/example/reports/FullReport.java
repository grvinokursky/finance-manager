package org.example.reports;

import java.util.List;

public class FullReport {
    private int totalIncome;
    private List<StatisticByIncomeCategory> statisticsByIncomeCategories;
    private int totalExpenses;
    private List<StatisticByExpensesCategory> statisticsByExpensesCategories;

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

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(int totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public List<StatisticByExpensesCategory> getStatisticsByExpensesCategories() {
        return statisticsByExpensesCategories;
    }

    public void setStatisticsByExpensesCategories(List<StatisticByExpensesCategory> statisticsByExpensesCategories) {
        this.statisticsByExpensesCategories = statisticsByExpensesCategories;
    }
}
