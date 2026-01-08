package org.example.models;

import java.util.UUID;

public class UserModel {
    private final UUID id;
    private String login;
    private String passwordHash;
    private WalletModel wallet;

    public UserModel() {
        this.id = UUID.randomUUID();
        this.wallet = new WalletModel();
    }

    public String getLogin() {
        return login;
    }

    public UUID getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public WalletModel getWallet() {
        return wallet;
    }

    public void setWallet(WalletModel wallet) {
        this.wallet = wallet;
    }
}
