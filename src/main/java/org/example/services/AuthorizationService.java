package org.example.services;

import org.example.models.UserModel;
import org.example.repository.UserRepository;
import org.example.security.PasswordHasher;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public AuthorizationService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
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

        var passwordHash = passwordHasher.hashPassword(password);

        userRepository.create(login, passwordHash);
    }

    public UserModel authorization(String login, String password) throws Exception {
        var user = userRepository.getByLogin(login);

        if (user.isEmpty()) {
            throw new Exception(String.format("Не удалось найти логин пользователя `%s`.", login));
        }

        if (!passwordHasher.checkPassword(password, user.get().getPasswordHash())) {
            throw new Exception("Был введен неверный пароль.");
        }

        return user.get();
    }
}
