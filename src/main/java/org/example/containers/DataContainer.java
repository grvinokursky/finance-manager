package org.example.containers;

import org.example.models.UserModel;
import org.example.models.WalletModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataContainer {
    private List<UserModel> users;

    public DataContainer() {
        users = new ArrayList<>();
    }

    public List<WalletModel> getWallets() {
        return users.stream()
                .map(UserModel::getWallet)
                .collect(Collectors.toList());
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
