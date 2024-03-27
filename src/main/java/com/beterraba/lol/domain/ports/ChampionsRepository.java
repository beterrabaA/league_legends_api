package com.beterraba.lol.domain.ports;

import com.beterraba.lol.domain.model.Champion;

import java.util.List;
import java.util.Optional;

public interface ChampionsRepository {
    List<Champion> findAll();
    Optional<Champion> findById(Long id);
}
