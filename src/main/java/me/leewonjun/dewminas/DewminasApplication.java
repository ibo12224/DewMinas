package me.leewonjun.dewminas;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DewminasApplication {
    public static void main(String args) {
        SpringApplication.run(DewminasApplication.class, args);
    }
}
