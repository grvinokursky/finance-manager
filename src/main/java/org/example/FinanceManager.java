package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class FinanceManager {
    static void main(String[] args) {
        // Раскомментировать вызов метода, если в консоли кракозябры.
        setOutEncoding();
        SpringApplication.run(FinanceManager.class, args);
    }

    private static void setOutEncoding() {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
    }
}
