package com.example.dockerdemo;

import org.springframework.data.repository.CrudRepository;

public interface HeroRepository extends CrudRepository<Hero, String> {

}
