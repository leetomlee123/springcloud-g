package com.example.webflux;

import com.example.webflux.model.Miui;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * @author lee
 */
@SpringBootApplication
public class WebfluxApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    public CommandLineRunner initData(MongoOperations mongo) {
        return (String... args) -> {
            mongo.dropCollection(Miui.class);
            mongo.createCollection(Miui.class, CollectionOptions.empty().maxDocuments(200).size(100000).capped());
        };
    }

}
