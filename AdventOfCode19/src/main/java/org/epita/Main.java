package org.epita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static int numberODifferentResources = 5;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}