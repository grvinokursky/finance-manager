package org.example.repository;

import org.example.models.UserModel;

import java.util.Optional;

public interface UserRepository {
    UserModel create(String login, String passwordHash) throws Exception;
    Optional<UserModel> getByLogin(String login);
}
