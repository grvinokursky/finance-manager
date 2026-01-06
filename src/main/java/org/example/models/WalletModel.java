package org.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WalletModel {
    private final UUID id;
    private List<IncomeCategoryModel> incomeCategories;
    private List<ExpensesCategoryModel> expensesCategories;
    private List<IncomeOperationModel> incomeOperations;
    private List<ExpensesOperationModel> expensesOperations;

    public WalletModel() {
        this.incomeCategories = new ArrayList<>();
        this.expensesCategories = new ArrayList<>();
        this.incomeOperations = new ArrayList<>();
        this.expensesOperations = new ArrayList<>();

        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public List<IncomeCategoryModel> getIncomeCategories() {
        return incomeCategories;
    }

    public void setIncomeCategories(List<IncomeCategoryModel> incomeCategories) {
        this.incomeCategories = incomeCategories;
    }

    public List<ExpensesCategoryModel> getExpensesCategories() {
        return expensesCategories;
    }

    public void setExpensesCategories(List<ExpensesCategoryModel> expensesCategories) {
        this.expensesCategories = expensesCategories;
    }

    public List<IncomeOperationModel> getIncomeOperations() {
        return incomeOperations;
    }

    public void setIncomeOperations(List<IncomeOperationModel> incomeOperations) {
        this.incomeOperations = incomeOperations;
    }

    public List<ExpensesOperationModel> getExpensesOperations() {
        return expensesOperations;
    }

    public void setExpensesOperations(List<ExpensesOperationModel> expensesOperations) {
        this.expensesOperations = expensesOperations;
    }
}
