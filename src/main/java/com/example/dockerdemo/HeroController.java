package com.example.dockerdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeroController {

    private final HeroRepository repository;

    public HeroController(HeroRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/heroes")
    public Iterable<Hero> findAll() {
        return repository
                .findAll();
    }

    @GetMapping("/heroes/{name}")
    public Hero findByName(@PathVariable("name") String name) {
        return repository
                .findById(name)
                .get();
    }
}