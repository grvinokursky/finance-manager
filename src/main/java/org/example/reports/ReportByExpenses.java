package org.example.reports;

import java.util.List;

public class ReportByExpenses {
    private long totalExpensesAtPennies;
    private List<StatisticByExpensesCategory> statisticsByExpensesCategories;

    public long getTotalExpenses() {
        return totalExpensesAtPennies;
    }

    public void setTotalExpenses(long totalExpensesAtPennies) {
        this.totalExpensesAtPennies = totalExpensesAtPennies;
    }

    public List<StatisticByExpensesCategory> getStatisticsByExpensesCategories() {
        return statisticsByExpensesCategories;
    }

    public void setStatisticsByExpensesCategories(List<StatisticByExpensesCategory> statisticsByExpensesCategories) {
        this.statisticsByExpensesCategories = statisticsByExpensesCategories;
    }
}
