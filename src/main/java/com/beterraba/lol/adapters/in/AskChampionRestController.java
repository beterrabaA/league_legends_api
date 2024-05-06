package com.beterraba.lol.adapters.in;

import com.beterraba.lol.application.AskChampionUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/champions")
public record AskChampionRestController(AskChampionUseCase useCase) {

    @CrossOrigin
    @PostMapping("/{id}/ask")
    public AskChampionResponse askChampion(@PathVariable Long id, @RequestBody AskChampionRequest request) {
        String answer = useCase.askChampions(id,request.question());
        return new AskChampionResponse(answer);
    }

    public record AskChampionRequest(String question) {};
    public record AskChampionResponse(String answer) {};

}
