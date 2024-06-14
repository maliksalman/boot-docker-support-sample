package com.example.dockerdemo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@Import(TestContainersConfiguration.class)
public class HeroRepositoryTests {

    @Autowired
    HeroRepository heroRepository;

    @Test
    void testFindAll() {
        AtomicInteger counter = new AtomicInteger();
        heroRepository
                .findAll()
                .forEach(h -> counter.incrementAndGet());
        Assertions.assertThat(counter.get()).isEqualTo(4);
    }

    @Test
    void testFindOne() {
        Optional<Hero> byId = heroRepository
                .findById("hero2");
        Assertions.assertThat(byId).isPresent();
        Assertions.assertThat(byId.get().age()).isEqualTo(200);
    }
}
