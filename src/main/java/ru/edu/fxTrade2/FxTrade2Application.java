package ru.edu.fxTrade2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories("ru.edu.fxTrade2.repository")
@EntityScan("ru.edu.fxTrade2.model")
public class FxTrade2Application {

	public static void main(String[] args) {
		SpringApplication.run(FxTrade2Application.class, args);
	}

}
