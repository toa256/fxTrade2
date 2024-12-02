package ru.edu.fxTrade2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.fxTrade2.model.Currency;
import ru.edu.fxTrade2.service.CurrencyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/currencies")
public class CoinGeckoController {

    @Autowired
    private CurrencyService currencyService;

    /**
     * Получение всех валют
     * @return список валют
     */
    @GetMapping
    public List<Currency> getAllCurrencies() { return currencyService.getAllCurrencies(); }

    /**
     * Получение валюты по ID
     * @param id идентификатор валюты
     * @return объект валюты
     */
    @GetMapping("/{id}")
    public ResponseEntity<Currency> getCurrencyById(@PathVariable Long id) {
        Optional<Currency> currency = currencyService.getCurrencyById(id);
        return currency.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Создание новой валюты
     * @param currency объект валюты
     * @return созданный объект валюты
     */
    @PostMapping
    public Currency createCurrency(@RequestBody Currency currency) { return currencyService.saveCurrency(currency); }

    /**
     * Обновление существующей валюты
     * @param id идентификатор валюты
     * @param currencyDetails детали обновляемой валюты
     * @return обновленный объект валюты
     */
    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable Long id, @RequestBody Currency currencyDetails) {
        Optional<Currency> currency = currencyService.getCurrencyById(id);
        if (currency.isPresent()) {
            Currency updatedCurrency = currency.get();
            updatedCurrency.setCode(currencyDetails.getCode());
            updatedCurrency.setName(currencyDetails.getName());
            updatedCurrency.setExchangeRate(currencyDetails.getExchangeRate());
            return ResponseEntity.ok(currencyService.saveCurrency(updatedCurrency));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удаление валюты по ID
     * @param id идентификатор валюты
     * @return статус ответа
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обновление курсов валют из CoinGecko
     * @return статус ответа
     */
    @PostMapping("/update-from-coingecko")
    public ResponseEntity<Void> updateCurrenciesFromCoinGecko() {
        currencyService.updateCurrenciesFromCoinGecko();
        return ResponseEntity.noContent().build();
    }
}

