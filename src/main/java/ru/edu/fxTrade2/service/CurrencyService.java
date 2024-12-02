package ru.edu.fxTrade2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.fxTrade2.model.Currency;
import ru.edu.fxTrade2.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CoinGeckoService coinGeckoService;

    public List<Currency> getAllCurrencies() { return currencyRepository.findAll(); }

    public Optional<Currency> getCurrencyById(Long id) { return currencyRepository.findById(id); }

    public Currency saveCurrency(Currency currency) { return currencyRepository.save(currency); }

    public void deleteCurrency(Long id) { currencyRepository.deleteById(id); }


    public void updateCurrenciesFromCoinGecko() {
        List<Currency> currenciesFromGecko = coinGeckoService.getCurrenciesFromCoinGecko();
        for (Currency currency : currenciesFromGecko) {
            Optional<Currency> existingCurrency = currencyRepository.findByCode(currency.getCode());
            if (existingCurrency.isPresent()) {
                Currency updatedCurrency = existingCurrency.get();
                updatedCurrency.setExchangeRate(currency.getExchangeRate());
                saveCurrency(updatedCurrency);
            } else {
                saveCurrency(currency);
            }
        }
    }
}
