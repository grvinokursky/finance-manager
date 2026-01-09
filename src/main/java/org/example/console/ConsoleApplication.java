package org.example.console;

import org.example.models.UserModel;
import org.example.services.AuthorizationService;
import org.example.services.WalletService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ConsoleApplication implements CommandLineRunner {
    private final AuthorizationService authorizationService;
    private final WalletService walletService;

    private UserModel currentUser;

    public ConsoleApplication(AuthorizationService authorizationService, WalletService walletService) {
        this.authorizationService = authorizationService;
        this.walletService = walletService;
    }

    @Override
    public void run(String... args) {
        while (true) {
            startAuthorization();
            startFinanceManagerService();
        }
    }

    private void startAuthorization() {
        var sc = new Scanner(System.in);

        System.out.println("Выполните авторизацию.");
        printAuthorizationHelp();

        while (true) {
            var commandLine =  sc.nextLine();
            var commandWords = commandLine.split(" ");

            if (commandWords.length == 3 && (Objects.equals(commandWords[0], "registration") || Objects.equals(commandWords[0], "reg"))) {
                try {
                    authorizationService.registration(commandWords[1], commandWords[2]);

                    System.out.println("Регистрация была выполнена успешно. Выполните авторизацию.");
                } catch (Exception e) {
                    System.out.printf("Не удалось выполнить регистрацию. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 3 && (Objects.equals(commandWords[0], "login") || Objects.equals(commandWords[0], "l"))) {
                try {
                    currentUser = authorizationService.authorization(commandWords[1], commandWords[2]);
                    System.out.println("Авторизация была выполнена успешно.");
                    return;
                } catch (Exception e) {
                    System.out.printf("Не удалось выполнить авторизацию. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 1 && Objects.equals(commandWords[0], "help")) {
                printAuthorizationHelp();
            } else {
                System.out.println("Не удалось распознать команду.");
            }
        }
    }

    private void startFinanceManagerService() {
        var sc = new Scanner(System.in);

        System.out.println("Запущен сервис управления финансами.");
        printFinanceManagerHelp();

        while (true) {
            var commandLine = sc.nextLine();
            var commandWords = commandLine.split(" ");

            if (commandWords.length >= 2 && (Objects.equals(commandWords[0], "create-income-category"))) {
                try {
                    var categoryName = String.join(" ", Arrays.stream(commandWords).toList().subList(1, commandWords.length));
                    walletService.createIncomeCategory(currentUser.getWallet().getId(), categoryName);
                    System.out.printf("Категория `%s` успешно добавлена.%n", categoryName);
                } catch (Exception e) {
                    System.out.printf("Не удалось добавить категорию доходов. %s%n", e.getMessage());
                }
            } else if (commandWords.length >= 3 && Objects.equals(commandWords[0], "create-expenses-category")) {
                try {
                    var categoryName = String.join(" ", Arrays.stream(commandWords).toList().subList(1, commandWords.length - 1));
                    var limitAtPennies = parseMoney(commandWords[commandWords.length - 1]);
                    walletService.createExpensesCategory(currentUser.getWallet().getId(), categoryName, limitAtPennies);
                    System.out.printf("Категория `%s` успешно добавлена.%n", categoryName);
                } catch (Exception e) {
                    System.out.printf("Не удалось добавить категорию расходов. %s%n", e.getMessage());
                }
            } else if (commandWords.length >= 3 && Objects.equals(commandWords[0], "add-income-operation")) {
                try {
                    var categoryName = String.join(" ", Arrays.stream(commandWords).toList().subList(1, commandWords.length - 1));
                    var valueAtPennies = parseMoney(commandWords[commandWords.length - 1]);
                    walletService.addIncomeOperation(currentUser.getWallet().getId(), categoryName, valueAtPennies);
                    System.out.println("Операция успешно добавлена.");
                } catch (Exception e) {
                    System.out.printf("Не удалось добавить операцию. %s%n", e.getMessage());
                }
            } else if (commandWords.length >= 3 && Objects.equals(commandWords[0], "add-expenses-operation")) {
                try {
                    var categoryName = String.join(" ", Arrays.stream(commandWords).toList().subList(1, commandWords.length - 1));
                    var valueAtPennies = parseMoney(commandWords[commandWords.length - 1]);
                    walletService.addExpensesOperation(currentUser.getWallet().getId(), categoryName, valueAtPennies);
                    System.out.println("Операция успешно добавлена.");
                    printWarningIfExceedExpendedCategoryLimit(categoryName);
                } catch (Exception e) {
                    System.out.printf("Не удалось добавить операцию. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 1 && Objects.equals(commandWords[0], "show-all-info")) {
                try {
                    var report = walletService.getFullReport(currentUser.getWallet().getId());

                    if (!report.getStatisticsByIncomeCategories().isEmpty()) {
                        System.out.printf("Общий доход: %s%n", moneyAtPenniesToString(report.getTotalIncomeAtPennies()));
                        System.out.println("Доходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByIncomeCategories()) {
                            System.out.printf("- %s: %s%n",
                                    statisticByCategory.getCategoryName(),
                                    moneyAtPenniesToString(statisticByCategory.getTotalIncomeAtPennies()));
                        }
                    } else {
                        System.out.println("Нет категорий доходов");
                    }

                    if (!report.getStatisticsByExpensesCategories().isEmpty()) {
                        System.out.printf("Общие расходы: %s%n", moneyAtPenniesToString(report.getTotalExpensesAtPennies()));
                        System.out.println("Расходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByExpensesCategories()) {
                            System.out.printf("- %s: %s, Оставшийся бюджет: %s%n",
                                    statisticByCategory.getCategoryName(),
                                    moneyAtPenniesToString(statisticByCategory.getTotalExpensesAtPennies()),
                                    moneyAtPenniesToString(statisticByCategory.getLimitAtPennies() - statisticByCategory.getTotalExpensesAtPennies()));
                        }
                    } else {
                        System.out.println("Нет категорий расходов");
                    }

                } catch (Exception e) {
                    System.out.printf("Не удалось получить статистику. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 1 && Objects.equals(commandWords[0], "show-info-by-income")) {
                try {
                    var report = walletService.getReportByIncome(currentUser.getWallet().getId());

                    if (!report.getStatisticsByIncomeCategories().isEmpty()) {
                        System.out.printf("Общий доход: %s%n", moneyAtPenniesToString(report.getTotalIncomeAtPennies()));
                        System.out.println("Доходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByIncomeCategories()) {
                            System.out.printf("- %s: %s%n",
                                    statisticByCategory.getCategoryName(),
                                    moneyAtPenniesToString(statisticByCategory.getTotalIncomeAtPennies()));
                        }
                    } else {
                        System.out.println("Нет категорий доходов");
                    }
                } catch (Exception e) {
                    System.out.printf("Не удалось получить статистику. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 1 && Objects.equals(commandWords[0], "show-info-by-expenses")) {
                try {
                    var report = walletService.getReportByExpenses(currentUser.getWallet().getId());

                    if (!report.getStatisticsByExpensesCategories().isEmpty()) {
                        System.out.printf("Общие расходы: %s%n", report.getTotalExpenses());
                        System.out.println("Расходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByExpensesCategories()) {
                            System.out.printf("- %s: %s, Оставшийся бюджет: %s%n",
                                    statisticByCategory.getCategoryName(),
                                    moneyAtPenniesToString(statisticByCategory.getTotalExpensesAtPennies()),
                                    moneyAtPenniesToString(statisticByCategory.getLimitAtPennies() - statisticByCategory.getTotalExpensesAtPennies()));
                        }
                    } else {
                        System.out.println("Нет категорий расходов");
                    }
                } catch (Exception e) {
                    System.out.printf("Не удалось получить статистику. %s%n", e.getMessage());
                }
            } else if (commandWords.length >= 2 && Objects.equals(commandWords[0], "show-info-by-categories:")) {
                try {
                    var categories = commandLine.split(": ")[1]
                            .split(" && ");

                    var report = walletService.getReportByCategories(currentUser.getWallet().getId(), Arrays.stream(categories).toList());

                    if (report.getStatisticsByIncomeCategories().isEmpty() && report.getStatisticsByExpensesCategories().isEmpty()) {
                        System.out.println("Не найдено статистики по указанным категориям.");
                        continue;
                    }

                    if (!report.getStatisticsByIncomeCategories().isEmpty()) {
                        System.out.println("Доходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByIncomeCategories()) {
                            System.out.printf("- %s: %s%n",
                                    statisticByCategory.getCategoryName(),
                                    moneyAtPenniesToString(statisticByCategory.getTotalIncomeAtPennies()));
                        }
                    }

                    if (!report.getStatisticsByExpensesCategories().isEmpty()) {
                        System.out.println("Расходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByExpensesCategories()) {
                            System.out.printf("- %s: %s, Оставшийся бюджет: %s%n",
                                    statisticByCategory.getCategoryName(),
                                    moneyAtPenniesToString(statisticByCategory.getTotalExpensesAtPennies()),
                                    moneyAtPenniesToString(statisticByCategory.getLimitAtPennies() - statisticByCategory.getTotalExpensesAtPennies()));
                        }
                    }
                } catch (Exception e) {
                    System.out.printf("Не удалось получить статистику. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 1 && Objects.equals(commandWords[0], "help")) {
                printFinanceManagerHelp();
            } else if (commandWords.length == 1 && Objects.equals(commandWords[0], "exit")) {
                System.out.println("Вы вышли из аккаунта.");
                return;
            } else {
                System.out.println("Невалидная команда, чтобы проверить допустимые команды введите `help`.");
            }
        }
    }

    private void printWarningIfExceedExpendedCategoryLimit(String categoryName) {
        try {
            if (walletService.checkExceedExpensesCategoryLimit(currentUser.getWallet().getId(), categoryName)) {
                System.out.printf("Обратите внимание, что по категории `%s` превышен лимит расходов.%n", categoryName);
            }
        } catch (Exception e) {
            // ignore
            // Если система будет развиваться можно будет писать в лог.
        }
    }

    private void printAuthorizationHelp() {
        System.out.println("Доступные команды:");
        System.out.println("help - Напечатать подсказки.");
        System.out.println("registration (сокращенно reg) <username> <password> - Регистрация пользователя (длина пароля должна быть не менее 6 символов).");
        System.out.println("login (сокращенно l) <username> <password> - Авторизация пользователя.");
    }

    private void printFinanceManagerHelp() {
        System.out.println("Доступные команды:");
        System.out.println("help - Напечатать подсказки.");
        System.out.println("create-income-category <categoryName> - Добавить категорию доходов.");
        System.out.println("create-expenses-category <expensesCategoryName> <limit> - Добавить категорию расходов, требуется указать лимит по расходам.");
        System.out.println("add-income-operation <categoryName> <value> - Добавить операцию доходов.");
        System.out.println("add-expenses-operation <categoryName> <value> - Добавить операцию расходов.");
        System.out.println("show-all-info - Отобразить общую статистику.");
        System.out.println("show-info-by-income - Отобразить статистику по доходам.");
        System.out.println("show-info-by-expenses - Отобразить статистику по расходам.");
        System.out.println("show-info-by-categories: <categoryName1> && <categoryName2> ... && <categoryNameN> - Отобразить статистику по выбранным категориям (перечислите категории через символы '&&').");
        System.out.println("exit - Выход из аккаунта.");
        System.out.println("При вводе копеек в денежных значениях в качестве разделителя используйте точку.");
    }

    private long parseMoney(String strValue) throws Exception {
        var moneyMask = "^\\d+(\\.\\d{1,2})?$";
        if (!strValue.matches(moneyMask)) {
            throw new Exception("Не удалось считать число, несоответствие денежному формату.");
        }

        try {
            var monetaryValueInParts = strValue.split("\\.");
            var value = Long.parseLong(monetaryValueInParts[0]) * 100;
            if (monetaryValueInParts.length == 2 && monetaryValueInParts[1].length() == 1) {
                value += Long.parseLong(monetaryValueInParts[1]) * 10;
            } else if (monetaryValueInParts.length == 2 && monetaryValueInParts[1].length() == 2) {
                value += Long.parseLong(monetaryValueInParts[1]);
            }

            return value;
        } catch (Exception e) {
            throw new Exception(String.format("Возникла неожиданная ошибка при конвертации числа `%s`.", strValue));
        }
    }

    private String moneyAtPenniesToString(long moneyAtPennies) {
        return BigDecimal.valueOf(moneyAtPennies, 2)
                .stripTrailingZeros()
                .toPlainString();
    }
}
