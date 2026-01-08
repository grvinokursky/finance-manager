package org.example.models;

public class ExpensesOperationModel {
    private ExpensesCategoryModel expensesCategoryModel;
    private long valueAtPennies;

    public ExpensesCategoryModel getExpensesCategoryModel() {
        return expensesCategoryModel;
    }

    public void setExpensesCategoryModel(ExpensesCategoryModel expensesCategoryModel) {
        this.expensesCategoryModel = expensesCategoryModel;
    }

    public long getValueAtPennies() {
        return valueAtPennies;
    }

    public void setValueAtPennies(long valueAtPennies) {
        this.valueAtPennies = valueAtPennies;
    }
}
