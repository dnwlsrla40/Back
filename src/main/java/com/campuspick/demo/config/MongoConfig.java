package com.campuspick.demo.config;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.campuspick.demo.repository", mongoTemplateRef = "CustomMongoTemplate")
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MongoTemplate CustomMongoTemplate(MongoClient mongoClient){
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(mongoClient, "campuspick");
        return new MongoTemplate(factory);
    }
}
