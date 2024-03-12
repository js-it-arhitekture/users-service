package com.example.nakup_vstopnic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import io.github.cdimascio.dotenv.Dotenv;
@SpringBootApplication
@EnableMongoRepositories
public class NakupVstopnicApplication {

    public static void main(String[] args) {
        SpringApplication.run(NakupVstopnicApplication.class, args);
    }

}
