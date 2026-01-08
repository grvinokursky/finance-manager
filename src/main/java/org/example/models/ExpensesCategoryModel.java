package org.example.models;

public class ExpensesCategoryModel {
    private String name;
    private long limitAtPennies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLimitAtPennies() {
        return limitAtPennies;
    }

    public void setLimitAtPennies(long limitAtPennies) {
        this.limitAtPennies = limitAtPennies;
    }
}
