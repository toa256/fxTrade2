package ru.edu.fxTrade2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.fxTrade2.model.Currency;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCode(String code);
}
