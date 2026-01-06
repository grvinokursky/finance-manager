package org.example.models;

public class ExpensesOperationModel {
    private ExpensesCategoryModel expensesCategoryModel;
    private int value;

    public ExpensesCategoryModel getExpensesCategoryModel() {
        return expensesCategoryModel;
    }

    public void setExpensesCategoryModel(ExpensesCategoryModel expensesCategoryModel) {
        this.expensesCategoryModel = expensesCategoryModel;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
