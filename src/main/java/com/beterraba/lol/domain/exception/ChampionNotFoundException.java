package com.beterraba.lol.domain.exception;

public class ChampionNotFoundException extends RuntimeException {
    public ChampionNotFoundException(Long championId) {
        super("Champion id:%d not found.".formatted(championId));
    }
}
