package com.example.dockerdemo;

import org.springframework.data.annotation.Id;

public record Hero(
        @Id String name,
        int age) {
}
