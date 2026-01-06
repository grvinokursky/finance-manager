package org.example.console;

import org.example.models.UserModel;
import org.example.services.AuthorizationService;
import org.example.services.WalletService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

            if (commandWords.length == 2 && (Objects.equals(commandWords[0], "create-income-category"))) {
                try {
                    walletService.createIncomeCategory(currentUser.getWallet().getId(), commandWords[1]);
                    System.out.printf("Категория `%s` успешно добавлена.%n", commandWords[1]);
                } catch (Exception e) {
                    System.out.printf("Не удалось добавить категорию доходов. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 3 && Objects.equals(commandWords[0], "create-expenses-category")) {
                try {
                    var limit = parseWithRuException(commandWords[2]);
                    walletService.createExpensesCategory(currentUser.getWallet().getId(), commandWords[1], limit);
                    System.out.printf("Категория `%s` успешно добавлена.%n", commandWords[1]);
                } catch (Exception e) {
                    System.out.printf("Не удалось добавить категорию расходов. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 3 && Objects.equals(commandWords[0], "add-income-operation")) {
                try {
                    var value = parseWithRuException(commandWords[2]);
                    walletService.addIncomeOperation(currentUser.getWallet().getId(), commandWords[1], value);
                    System.out.println("Операция успешно добавлена.");
                } catch (Exception e) {
                    System.out.printf("Не удалось добавить операцию. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 3 && Objects.equals(commandWords[0], "add-expenses-operation")) {
                try {
                    var value = parseWithRuException(commandWords[2]);
                    walletService.addExpensesOperation(currentUser.getWallet().getId(), commandWords[1], value);
                    System.out.println("Операция успешно добавлена.");
                } catch (Exception e) {
                    System.out.printf("Не удалось добавить операцию. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 1 && Objects.equals(commandWords[0], "show-all-info")) {
                try {
                    var report = walletService.getFullReport(currentUser.getWallet().getId());

                    if (!report.getStatisticsByIncomeCategories().isEmpty()) {
                        System.out.printf("Общий доход: %s%n", report.getTotalIncome());
                        System.out.println("Доходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByIncomeCategories()) {
                            System.out.printf("-%s: %s%n", statisticByCategory.getCategoryName(), statisticByCategory.getTotalIncome());
                        }
                    } else {
                        System.out.println("Нет категорий доходов");
                    }

                    if (!report.getStatisticsByExpensesCategories().isEmpty()) {
                        System.out.printf("Общие расходы: %s%n", report.getTotalExpenses());
                        System.out.println("Расходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByExpensesCategories()) {
                            System.out.printf("-%s: %s, Оставшийся бюджет: %s%n",
                                    statisticByCategory.getCategoryName(),
                                    statisticByCategory.getTotalExpenses(),
                                    statisticByCategory.getLimit() - statisticByCategory.getTotalExpenses());
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
                        System.out.printf("Общий доход: %s%n", report.getTotalIncome());
                        System.out.println("Доходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByIncomeCategories()) {
                            System.out.printf("-%s: %s%n", statisticByCategory.getCategoryName(), statisticByCategory.getTotalIncome());
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
                            System.out.printf("-%s: %s, Оставшийся бюджет: %s%n",
                                    statisticByCategory.getCategoryName(),
                                    statisticByCategory.getTotalExpenses(),
                                    statisticByCategory.getLimit() - statisticByCategory.getTotalExpenses());
                        }
                    } else {
                        System.out.println("Нет категорий расходов");
                    }
                } catch (Exception e) {
                    System.out.printf("Не удалось получить статистику. %s%n", e.getMessage());
                }
            } else if (commandWords.length == 2 && Objects.equals(commandWords[0], "show-info-by-categories")) {
                try {
                    var categories = commandWords[1].split(",");
                    var report = walletService.getReportByCategories(currentUser.getWallet().getId(), categories);

                    if (report.getStatisticsByIncomeCategories().isEmpty() && report.getStatisticsByExpensesCategories().isEmpty()) {
                        System.out.println("Не найдено статистики по указанным категориям.");
                        continue;
                    }

                    if (!report.getStatisticsByIncomeCategories().isEmpty()) {
                        System.out.println("Доходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByIncomeCategories()) {
                            System.out.printf("-%s: %s%n", statisticByCategory.getCategoryName(), statisticByCategory.getTotalIncome());
                        }
                    }

                    if (!report.getStatisticsByExpensesCategories().isEmpty()) {
                        System.out.println("Расходы по категориям:");
                        for (var statisticByCategory : report.getStatisticsByExpensesCategories()) {
                            System.out.printf("-%s: %s, Оставшийся бюджет: %s%n",
                                    statisticByCategory.getCategoryName(),
                                    statisticByCategory.getTotalExpenses(),
                                    statisticByCategory.getLimit() - statisticByCategory.getTotalExpenses());
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
                System.out.println("Не удалось распознать команду.");
            }
        }
    }

    private void printAuthorizationHelp() {
        System.out.println("Доступные команды:");
        System.out.println("help - Напечатать подсказки.");
        System.out.println("registration (сокращенно reg) <username> <password> - Регистрация пользователя.");
        System.out.println("login (сокращенно l) <username> <password> - Авторизация пользователя.");
    }

    private void printFinanceManagerHelp() {
        System.out.println("Доступные команды:");
        System.out.println("help - Напечатать подсказки.");
        System.out.println("create-income-category <categoryName> - Добавить категорию доходов.");
        System.out.println("create-expenses-category <expensesCategoryName> <limit> - Добавить категорию расходов, требуется указать лимит по расходам (лимит должен быть целым числом).");
        System.out.println("add-income-operation <categoryName> <value> - Добавить операцию доходов.");
        System.out.println("add-expenses-operation <categoryName> <value> - Добавить операцию расходов.");
        System.out.println("show-all-info - Отобразить общую статистику.");
        System.out.println("show-info-by-income - Отобразить статистику по доходам.");
        System.out.println("show-info-by-expenses - Отобразить статистику по расходам.");
        System.out.println("show-info-by-categories <categoryName1>,<categoryName2>,...<categoryNameN> - Отобразить статистику по выбранным категориям (перечислите категории через запятую без пробелов.)");
        System.out.println("exit - Выход из аккаунта.");
    }

    private int parseWithRuException(String value) throws Exception {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new Exception(String.format("Не удалось считать значение - `%s` не является целым числом.", value));
        }
    }
}
