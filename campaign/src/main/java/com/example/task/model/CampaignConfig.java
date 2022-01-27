package com.example.task.model;

import com.example.task.repository.CampaignRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CampaignConfig {

    @Bean
    CommandLineRunner commandLineRunner(CampaignRepository repository) {
        return args -> {
            Campaign one =  new Campaign(
                    "first",
                    11500,
                    3000,
                    true,
                    Town.CRACOW,
                    2500
            );
            Campaign two =  new Campaign(
                    "second",
                    2100,
                    300,
                    false,
                    Town.WARSAW,
                    12
            );
            Campaign three =  new Campaign(
                    "third",
                    3333,
                    12345,
                    true,
                    Town.LODZ,
                    420
            );
            Campaign four =  new Campaign(
                    "fourth",
                    121212,
                    1,
                    false,
                    Town.POZNAN,
                    456
            );
            repository.saveAll(List.of(one, two, three, four));
        };
    }
}
