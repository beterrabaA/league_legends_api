package com.beterraba.lol.application;

import com.beterraba.lol.domain.model.Champion;
import com.beterraba.lol.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase(ChampionsRepository repository) {

    public List<Champion> findAll() {
        return repository.findAll();
    }
}
