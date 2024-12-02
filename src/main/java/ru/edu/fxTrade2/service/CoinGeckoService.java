package ru.edu.fxTrade2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.edu.fxTrade2.model.Currency;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoinGeckoService {

    @Value("${coingecko.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Получение списка валют с CoinGecko
     * @return список валют
     */
    public List<Currency> getCurrenciesFromCoinGecko() {
        String url = apiUrl + "/coins/markets?vs_currency=usd";

        // Выполнение запроса
        CoinGeckoCurrency[] response = restTemplate.getForObject(url, CoinGeckoCurrency[].class);

        // Преобразование ответа в List<Currency>
        return response != null ?
                List.of(response).stream().map(this::convertToCurrency).collect(Collectors.toList()) :
                List.of();
    }

    /**
     * Преобразование данных CoinGecko в объект Currency
     * @param gkCurrency данные валюты с CoinGecko
     * @return объект Currency
     */
    private Currency convertToCurrency(CoinGeckoCurrency gkCurrency) {
        Currency currency = new Currency();
        currency.setCode(gkCurrency.getSymbol().toUpperCase());
        currency.setName(gkCurrency.getName());
        currency.setExchangeRate(gkCurrency.getCurrentPrice());
        return currency;
    }

    // Внутренние классы для маппинга ответа
    public static class CoinGeckoCurrency {
        private String id;
        private String symbol;
        private String name;
        private double current_price;

        public String getId() { return id; }

        public void setId(String id) { this.id = id; }

        public String getSymbol() { return symbol; }

        public void setSymbol(String symbol) { this.symbol = symbol; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public double getCurrentPrice() {
            return current_price;
        }

        public void setCurrentPrice(double current_price) {
            this.current_price = current_price;
        }
    }
}

