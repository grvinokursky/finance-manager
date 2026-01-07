package org.example.repository;

import org.example.containers.DataContainer;
import org.example.models.*;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

@Repository
public class WalletRepositoryInMemory implements WalletRepository {
    private final DataContainer dataContainer;

    public WalletRepositoryInMemory(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }

    @Override
    public void createIncomeCategory(UUID walletId, String name) throws Exception {
        var wallet = getWallet(walletId);

        if (wallet.getIncomeCategories().stream().anyMatch(x -> Objects.equals(x.getName(), name))) {
            throw new Exception(String.format("Для пользователя уже существует категория доходов с наименованием '%s'.", name));
        }

        var incomeCategory = new IncomeCategoryModel();
        incomeCategory.setName(name);

        wallet.getIncomeCategories().add(incomeCategory);
        dataContainer.saveData();
    }

    @Override
    public void createExpensesCategory(UUID walletId, String name, int limit) throws Exception {
        var wallet = getWallet(walletId);

        if (wallet.getExpensesCategories().stream().anyMatch(x -> Objects.equals(x.getName(), name))) {
            throw new Exception(String.format("Для пользователя уже существует категория расходов с наименованием '%s'.", name));
        }

        var expensesCategory = new ExpensesCategoryModel();
        expensesCategory.setName(name);
        expensesCategory.setLimit(limit);

        wallet.getExpensesCategories().add(expensesCategory);
        dataContainer.saveData();
    }

    @Override
    public void addIncomeOperation(UUID walletId, String categoryName, int value) throws Exception {
        var wallet = getWallet(walletId);

        var incomeCategory = wallet.getIncomeCategories().stream().filter(x -> Objects.equals(x.getName(), categoryName)).findFirst();
        if (incomeCategory.isEmpty()) {
            throw new Exception(String.format("Невозможно добавить операцию так как не существует указанная категория доходов `%s` к которой относится данная операция.", categoryName));
        }

        var incomeOperation = new IncomeOperationModel();
        incomeOperation.setIncomeCategoryModel(incomeCategory.get());
        incomeOperation.setValue(value);

        wallet.getIncomeOperations().add(incomeOperation);
        dataContainer.saveData();
    }

    @Override
    public void addExpensesOperation(UUID walletId, String categoryName, int value) throws Exception {
        var wallet = getWallet(walletId);

        var expensesCategory = wallet.getExpensesCategories().stream().filter(x -> Objects.equals(x.getName(), categoryName)).findFirst();
        if (expensesCategory.isEmpty()) {
            throw new Exception(String.format("Невозможно добавить операцию так как не существует указанная категория расходов `%s` к которой относится данная операция.", categoryName));
        }

        var expensesOperation = new ExpensesOperationModel();
        expensesOperation.setExpensesCategoryModel(expensesCategory.get());
        expensesOperation.setValue(value);

        wallet.getExpensesOperations().add(expensesOperation);
        dataContainer.saveData();
    }

    @Override
    public WalletModel getWallet(UUID walletId) throws Exception {
        var wallet = dataContainer.getWallets().stream().filter(w -> Objects.equals(w.getId(), walletId)).findFirst();
        if (wallet.isEmpty()) {
            throw new Exception(String.format("Не удалось найти указанный кошелек (ID `%s`)", walletId));
        }

        return wallet.get();
    }
}
