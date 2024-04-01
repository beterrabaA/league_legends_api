package com.beterraba.lol.application;

import com.beterraba.lol.domain.exception.ChampionNotFoundException;
import com.beterraba.lol.domain.model.Champion;
import com.beterraba.lol.domain.ports.ChampionsRepository;

public record AskChampionUseCase(ChampionsRepository repository) {

    public String askChampions(Long championId, String question) {

        Champion champion = repository.findById(championId).orElseThrow(() -> new ChampionNotFoundException(championId));

        return champion.generateContextByQuestion(question);
    }
}
