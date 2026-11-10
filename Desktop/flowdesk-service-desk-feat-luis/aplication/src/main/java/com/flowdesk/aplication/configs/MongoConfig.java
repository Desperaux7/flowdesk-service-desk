package com.flowdesk.aplication.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@Configuration
public class MongoConfig {
    @Bean
    CommandLineRunner checkDatabase(
            MongoDatabaseFactory factory){
        return args -> System.out.println("MongoDB database: " + factory.getMongoDatabase().getName());
    }
}
