package org.example.repository;

import org.example.models.WalletModel;

import java.util.UUID;

public interface WalletRepository {
    void createIncomeCategory(UUID walletId, String name) throws Exception;
    void createExpensesCategory(UUID walletId, String name, int limit) throws Exception;
    void addIncomeOperation(UUID walletId, String categoryName, int value) throws Exception;
    void addExpensesOperation(UUID walletId, String categoryName, int value) throws Exception;
    WalletModel getWallet(UUID walletId) throws Exception;
}
