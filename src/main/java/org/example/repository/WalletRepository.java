package org.example.repository;

import org.example.models.WalletModel;

import java.util.UUID;

public interface WalletRepository {
    void createIncomeCategory(UUID walletId, String name) throws Exception;
    void createExpensesCategory(UUID walletId, String name, long limitAtPennies) throws Exception;
    void addIncomeOperation(UUID walletId, String categoryName, long valueAtPennies) throws Exception;
    void addExpensesOperation(UUID walletId, String categoryName, long valueAtPennies) throws Exception;
    WalletModel getWallet(UUID walletId) throws Exception;
}
