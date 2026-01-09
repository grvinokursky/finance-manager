package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class FinanceManager {
    static void main(String[] args) {
        setEncoding();

        SpringApplication.run(FinanceManager.class, args);
    }

    private static void setEncoding() {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
    }
}
