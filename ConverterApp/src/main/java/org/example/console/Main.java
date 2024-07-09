package org.example.console;

import org.example.repository.CurrencyRepository;
import org.example.service.CurrencyService;
import org.example.web.CurrencyRateFetcher;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        System.out.println("Приложение конвертер валют");
        CurrencyRateFetcher currencyRateFetcher = new CurrencyRateFetcher();
        CurrencyService currencyService = new CurrencyService(new CurrencyRateFetcher(), new CurrencyRepository());

        System.out.println("Загрузка валюты в базу данных...");
        currencyService.loadCurrencyToDataBase();
        System.out.println("Валюты загружены");
        System.out.println("----------------");
        while (true) {
            System.out.println("Введите валюту для перевода (пример 10 USD), либо введите 0 для выхода");
            Scanner scanner = new Scanner(System.in);

            String input = scanner.nextLine();
            // Регулярное выражение для проверки формата "10" или "10.XXX" и трёхбуквенного обозначения валюты
            String regex = "^10(?:\\.\\d{1,3})? [A-Z]{3}$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);

            if (input.equals("0")) {
                return;
            } else if (!matcher.matches()) {
                System.out.println("Неверный формат. Пожалуйста, введите значение в формате '10 USD' или '10.XXX USD'.");
            } else {
                System.out.println(currencyService.getRubCurrencyByAnother(input) + " RUB");
            }
        }
    }
}