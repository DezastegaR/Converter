package org.example.repository;

import org.example.model.Currency;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CurrencyRepository {
    private String url = "jdbc:postgresql://localhost:5432/CurrencyConverter";
    private String user = "postgres";
    private String password = "qwerty";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void insertCurrency(List<Currency> currencies) {
        String sql = "INSERT INTO CURRENCY_EXCHANGE_RATE (CURRENCY_CODE, CURRENCY_RATE) VALUES (?, ?) " +
                "ON CONFLICT (CURRENCY_CODE) DO UPDATE SET CURRENCY_RATE = EXCLUDED.CURRENCY_RATE";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Currency currency : currencies) {
                pstmt.setString(1, currency.getCharCode());
                pstmt.setDouble(2, currency.getValue());
                pstmt.addBatch(); // Добавляем в пакет
            }
            pstmt.executeBatch(); // Выполняем пакетную вставку
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getCurrencyRate(String currencyCode) {
        String sql = "SELECT CURRENCY_RATE FROM CURRENCY_EXCHANGE_RATE WHERE CURRENCY_CODE = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, currencyCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("CURRENCY_RATE");
                } else {
                    System.out.println("Такой валюты не существует");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
