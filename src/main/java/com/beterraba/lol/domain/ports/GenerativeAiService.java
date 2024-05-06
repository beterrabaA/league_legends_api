package com.beterraba.lol.domain.ports;

public interface GenerativeAiService {

    String generateContent(String objective, String context);
}