package com.beterraba.lol.adapters.in;

import com.beterraba.lol.application.ListChampionsUseCase;
import com.beterraba.lol.domain.model.Champion;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "champions",description = "endpoints that list champions")
@RestController
@RequestMapping("/champions")
public record ListChampionsRestController(ListChampionsUseCase useCase) {

    @GetMapping("/")
    public List<Champion> findAllChampions() {
        return useCase.findAll();
    }
}
