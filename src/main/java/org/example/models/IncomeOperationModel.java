package org.example.models;

public class IncomeOperationModel {
    private IncomeCategoryModel incomeCategoryModel;
    private int value;

    public IncomeCategoryModel getIncomeCategoryModel() {
        return incomeCategoryModel;
    }

    public void setIncomeCategoryModel(IncomeCategoryModel incomeCategoryModel) {
        this.incomeCategoryModel = incomeCategoryModel;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
