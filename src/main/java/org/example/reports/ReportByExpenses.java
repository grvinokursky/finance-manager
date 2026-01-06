package org.example.reports;

import java.util.List;

public class ReportByExpenses {
    private int totalExpenses;
    private List<StatisticByExpensesCategory> statisticsByExpensesCategories;

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
