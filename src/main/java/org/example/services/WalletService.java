package org.example.services;

import org.example.models.ExpensesOperationModel;
import org.example.models.IncomeOperationModel;
import org.example.models.WalletModel;
import org.example.reports.*;
import org.example.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void createIncomeCategory(UUID walletId, String name) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("Наименование категории не может быть пустым.");
        }

        walletRepository.createIncomeCategory(walletId, name);
    }

    public void createExpensesCategory(UUID walletId, String name, int limit) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("Наименование категории не может быть пустым.");
        }

        if (limit<=0) {
            throw new Exception("Значение лимита должно быть положительным числом");
        }

        walletRepository.createExpensesCategory(walletId, name, limit);
    }

    public void addIncomeOperation(UUID walletId, String categoryName, int value) throws Exception {
        if (categoryName.isEmpty()) {
            throw new Exception("Наименование категории не может быть пустым.");
        }

        if (value<=0) {
            throw new Exception("Значение операции должно быть положительным числом");
        }

        walletRepository.addIncomeOperation(walletId, categoryName, value);
    }

    public void addExpensesOperation(UUID walletId, String categoryName, int value) throws Exception {
        if (categoryName.isEmpty()) {
            throw new Exception("Наименование категории не может быть пустым.");
        }

        if (value<=0) {
            throw new Exception("Значение операции должно быть положительным числом");
        }

        walletRepository.addExpensesOperation(walletId, categoryName, value);
    }

    public boolean checkExceedExpensesCategoryLimit(UUID walletId, String categoryName) throws Exception {
        var wallet = walletRepository.getWallet(walletId);
        var category = wallet.getExpensesCategories().stream().filter(c -> Objects.equals(categoryName, c.getName())).findFirst();
        if (category.isEmpty()) {
            throw new Exception(String.format("Не удалось найти категорию `%s`.", categoryName));
        }

        var sumCategoryOperations = wallet.getExpensesOperations().stream()
                .filter(o -> Objects.equals(categoryName, o.getExpensesCategoryModel().getName()))
                .mapToInt(ExpensesOperationModel::getValue)
                .sum();

        return category.get().getLimit() < sumCategoryOperations;
    }

    public FullReport getFullReport(UUID walletId) throws Exception {
        var wallet = walletRepository.getWallet(walletId);
        var report = new FullReport();
        report.setTotalIncome(getTotalIncome(wallet));
        report.setStatisticsByIncomeCategories(getStatisticsByIncomeCategories(wallet));
        report.setTotalExpenses(getTotalExpenses(wallet));
        report.setStatisticsByExpensesCategories(getStatisticsByExpensesCategories(wallet));
        return report;
    }

    public ReportByIncome getReportByIncome(UUID walletId) throws Exception {
        var wallet = walletRepository.getWallet(walletId);
        var report = new ReportByIncome();
        report.setTotalIncome(getTotalIncome(wallet));
        report.setStatisticsByIncomeCategories(getStatisticsByIncomeCategories(wallet));
        return report;
    }

    public ReportByExpenses getReportByExpenses(UUID walletId) throws Exception {
        var wallet = walletRepository.getWallet(walletId);
        var report = new ReportByExpenses();
        report.setTotalExpenses(getTotalExpenses(wallet));
        report.setStatisticsByExpensesCategories(getStatisticsByExpensesCategories(wallet));
        return report;
    }

    public ReportByCategories getReportByCategories(UUID walletId, List<String> categoryNames) throws Exception {
        var wallet = walletRepository.getWallet(walletId);
        var report = new ReportByCategories();
        report.setStatisticsByIncomeCategories(getStatisticsByIncomeCategories(wallet, categoryNames));
        report.setStatisticsByExpensesCategories(getStatisticsByExpensesCategories(wallet, categoryNames));
        return report;
    }

    private int getTotalIncome(WalletModel wallet) {
        return wallet.getIncomeOperations().stream()
                .mapToInt(IncomeOperationModel::getValue)
                .sum();
    }

    private int getTotalExpenses(WalletModel wallet) {
        return wallet.getExpensesOperations().stream()
                .mapToInt(ExpensesOperationModel::getValue)
                .sum();
    }

    private List<StatisticByIncomeCategory> getStatisticsByIncomeCategories(WalletModel wallet) {
        return wallet.getIncomeCategories().stream()
                .map(x -> new StatisticByIncomeCategory(
                        x.getName(),
                        wallet.getIncomeOperations().stream()
                                .filter(o -> Objects.equals(o.getIncomeCategoryModel().getName(), x.getName()))
                                .mapToInt(IncomeOperationModel::getValue)
                                .sum()))
                .toList();
    }

    private List<StatisticByIncomeCategory> getStatisticsByIncomeCategories(WalletModel wallet, List<String> categoryNames) {
        return wallet.getIncomeCategories().stream()
                .filter(c -> categoryNames.stream()
                        .anyMatch(categoryName -> Objects.equals(categoryName, c.getName())))
                .map(x -> new StatisticByIncomeCategory(
                        x.getName(),
                        wallet.getIncomeOperations().stream()
                                .filter(o -> Objects.equals(o.getIncomeCategoryModel().getName(), x.getName()))
                                .mapToInt(IncomeOperationModel::getValue)
                                .sum()))
                .toList();
    }

    private List<StatisticByExpensesCategory> getStatisticsByExpensesCategories(WalletModel wallet) {
        return wallet.getExpensesCategories().stream()
                .map(x -> new StatisticByExpensesCategory(
                        x.getName(),
                        wallet.getExpensesOperations().stream()
                                .filter(o -> Objects.equals(o.getExpensesCategoryModel().getName(), x.getName()))
                                .mapToInt(ExpensesOperationModel::getValue)
                                .sum(),
                        x.getLimit()))
                .toList();
    }

    private List<StatisticByExpensesCategory> getStatisticsByExpensesCategories(WalletModel wallet, List<String> categoryNames) {
        return wallet.getExpensesCategories().stream()
                .filter(c -> categoryNames.stream()
                        .anyMatch(categoryName -> Objects.equals(categoryName, c.getName())))
                .map(x -> new StatisticByExpensesCategory(
                        x.getName(),
                        wallet.getExpensesOperations().stream()
                                .filter(o -> Objects.equals(o.getExpensesCategoryModel().getName(), x.getName()))
                                .mapToInt(ExpensesOperationModel::getValue)
                                .sum(),
                        x.getLimit()))
                .toList();
    }
}
