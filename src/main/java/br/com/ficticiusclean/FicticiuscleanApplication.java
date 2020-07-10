package br.com.ficticiusclean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories
@SpringBootApplication
public class FicticiuscleanApplication {

    public static void main(String[] args) {
        SpringApplication.run(FicticiuscleanApplication.class, args);
    }

}
