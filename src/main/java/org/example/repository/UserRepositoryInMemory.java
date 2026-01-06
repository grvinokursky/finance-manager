package org.example.repository;

import org.example.containers.DataContainer;
import org.example.models.UserModel;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepositoryInMemory implements UserRepository {
    private final DataContainer dataContainer;

    public UserRepositoryInMemory(DataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }

    @Override
    public UserModel create(String login, String password) throws Exception {
        if (dataContainer.getUsers().stream().anyMatch(u -> Objects.equals(u.getLogin(), login))) {
            throw new Exception(String.format("Пользователь с указанным логином '%s' уже существует.", login));
        }

        var user = new UserModel();
        user.setLogin(login);
        user.setPassword(password);

        dataContainer.getUsers().add(user);

        return user;
    }

    @Override
    public Optional<UserModel> getByLogin(String login) {
        return dataContainer.getUsers().stream().filter(u -> Objects.equals(u.getLogin(), login)).findFirst();
    }
}
