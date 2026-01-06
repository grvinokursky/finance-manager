package org.example.services;

import org.example.models.UserModel;
import org.example.repository.UserRepository;
import org.example.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthorizationService {
    private UserRepository userRepository;
    private WalletRepository walletRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registration(String login, String password) throws Exception {
        if (login.isEmpty()) {
            throw new Exception("Логин не может быть пустым.");
        }

        if (password.isEmpty()) {
            throw new Exception("Пароль не может быть пустым.");
        }

        if (password.length() < 6) {
            throw new Exception(" Пароль должен иметь длину не менее 6 символов.");
        }

        var user = userRepository.create(login, password);
    }

    public UserModel authorization(String login, String password) throws Exception {
        var user = userRepository.getByLogin(login);

        if (user.isEmpty()) {
            throw new Exception(String.format("Не удалось найти логин пользователя `%s`.", login));
        }

        if (!Objects.equals(user.get().getPassword(), password)) {
            throw new Exception("Был введен неверный пароль.");
        }

        return user.get();
    }
}
