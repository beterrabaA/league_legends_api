package com.beterraba.lol.application;

import com.beterraba.lol.domain.exception.ChampionNotFoundException;
import com.beterraba.lol.domain.model.Champion;
import com.beterraba.lol.domain.ports.ChampionsRepository;
import com.beterraba.lol.domain.ports.GenerativeAiService;

public record AskChampionUseCase(ChampionsRepository repository, GenerativeAiService genAiService) {

    public String askChampions(Long championId, String question) {

        Champion champion = repository.findById(championId).orElseThrow(() -> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);

        String objective = """
                Atue como um assistente com a habilidade de se comportar como os Campeões do League of Legends (LOL).
                Responsa perguntas incorporando a personalidade e estilo de um determinado Campeão.
                Segue a pergunta, o nome do Campeão e sua respectiva lore (história):
                
                """;

        return genAiService.generateContent(objective, context);
    }
}
