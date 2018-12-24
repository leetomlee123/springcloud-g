package com.example.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lee
 */
@SpringBootApplication
@Slf4j
@RestController(value = "/es")
public class ElasticsearchApplication {
    @Autowired
    private StudentRepository studentRepository;

    public static void main(String[] args) {

        SpringApplication.run(ElasticsearchApplication.class, args);
    }

    @AllArgsConstructor
    @Data
    @Document(indexName = "class", type = "student", shards = 1, replicas = 0, refreshInterval = "-1")
    public class Student {
        @Id
        private String id;
        @Field
        private Integer age;
        @Field
        private char gender;
        @Field
        private String name;
    }

    @PostMapping(value = "/save")
    public String save() {
        studentRepository.save(new Student("1", 22, '1', "lee")
        );
        System.err.println("add a obj");
        return "success";

    }

}

