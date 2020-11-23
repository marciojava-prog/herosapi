package br.dev.marciooliveira.herosapi.controller;


import br.dev.marciooliveira.herosapi.document.Heroes;
import br.dev.marciooliveira.herosapi.repository.HeroesRepository;
import br.dev.marciooliveira.herosapi.service.HeroesService;
import org.springframework.stereotype.Controller;
import br.dev.marciooliveira.herosapi.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static br.dev.marciooliveira.herosapi.constans.HeroesConstat.*;

@RestController
@Slf4j
public class HeroesController {

    HeroesService heroesService;
    HeroesRepository heroesRepository;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HeroesController.class);

    public HeroesController(HeroesService heroesService, HeroesRepository heroesRepository){
        this.heroesService = heroesService;
        this.heroesRepository = heroesRepository;

    }

    @GetMapping(HEROES_ENDPOINT_LOCAL)
    public Flux<Heroes> getAllItems(){
        log.info("requesting the list of all heroes");
        return heroesService.findAll();
    }
    @GetMapping(HEROES_ENDPOINT_LOCAL + "/id")
    public Mono <ResponseEntity<Heroes>> findByIdHero(@PathVariable String id){
        log.info("Resquesting the hero with id {}", id);
        return heroesService.findByIdHero(id)
                .map((item) -> new ResponseEntity<>(item,HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Heroes> createHero(@RequestBody Heroes heroes){
        log.info("a new hero was created");
        return heroesService.save(heroes);
    }
    @DeleteMapping(HEROES_ENDPOINT_LOCAL + "/id")
    @ResponseStatus(code = HttpStatus.CONTINUE)
    public Mono<HttpStatus> deleteByIdHero(@PathVariable String id){
        heroesService.deleteByIdHero(id);
        log.info("Hero delete  with sucess {}", id);
        return Mono.just(HttpStatus.CONTINUE);

    }
}
