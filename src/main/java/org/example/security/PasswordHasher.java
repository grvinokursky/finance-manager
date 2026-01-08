package org.example.security;

public interface PasswordHasher {
    String hashPassword(String plainPassword);
    boolean checkPassword(String plainPassword, String hashedPassword);
}
