package org.example.service;

import org.example.model.CurrencyRates;
import org.example.repository.CurrencyRepository;
import org.example.web.CurrencyRateFetcher;

import java.util.ArrayList;

public class CurrencyService {
    private final CurrencyRateFetcher currencyRateFetcher;
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRateFetcher currencyRateFetcher, CurrencyRepository currencyRepository) {
        this.currencyRateFetcher = currencyRateFetcher;
        this.currencyRepository = currencyRepository;
    }


    public void loadCurrencyToDataBase() {
        try {
            CurrencyRates currencyRates = currencyRateFetcher.fetchRates();
            currencyRepository.insertCurrency(new ArrayList<>(currencyRates.getValute().values()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public double getRubCurrencyByAnother(String lineFromUser) {
        String[] userInputs = lineFromUser.split(" ");
        double currencyRate = currencyRepository.getCurrencyRate(userInputs[1]);
        return currencyRate * Double.parseDouble(userInputs[0]);
    }
}
