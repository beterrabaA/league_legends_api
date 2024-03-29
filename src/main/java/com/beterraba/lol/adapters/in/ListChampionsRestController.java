package com.beterraba.lol.adapters.in;

import com.beterraba.lol.application.ListChampionsUseCase;
import com.beterraba.lol.domain.model.Champion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/champions")
public record ListChampionsRestController(ListChampionsUseCase useCase) {

    @GetMapping("/")
    public List<Champion> findAllChampions() {
        return useCase.findAll();
    }
}
