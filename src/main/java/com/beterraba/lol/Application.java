package com.beterraba.lol;

import com.beterraba.lol.application.AskChampionUseCase;
import com.beterraba.lol.application.ListChampionsUseCase;
import com.beterraba.lol.domain.ports.ChampionsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository repository) {
		return new ListChampionsUseCase(repository);
	}

	@Bean
	public AskChampionUseCase provideAskChampionUseCase(ChampionsRepository repository) {
		return new AskChampionUseCase(repository);
	}

}
