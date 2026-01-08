package org.example.models;

public class IncomeOperationModel {
    private IncomeCategoryModel incomeCategoryModel;
    private long valueAtPennies;

    public IncomeCategoryModel getIncomeCategoryModel() {
        return incomeCategoryModel;
    }

    public void setIncomeCategoryModel(IncomeCategoryModel incomeCategoryModel) {
        this.incomeCategoryModel = incomeCategoryModel;
    }

    public long getValueAtPennies() {
        return valueAtPennies;
    }

    public void setValueAtPennies(long value) {
        this.valueAtPennies = value;
    }
}
