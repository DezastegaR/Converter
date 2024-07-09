package org.example.web;

import com.google.gson.Gson;
import org.example.model.CurrencyRates;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyRateFetcher {
    private String api_url = "https://www.cbr-xml-daily.ru/daily_json.js";


    public CurrencyRates fetchRates() throws Exception {
        URL url = new URL(api_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (Reader reader = new InputStreamReader(connection.getInputStream())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, CurrencyRates.class);
        }
    }
}
