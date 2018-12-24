package com.example.message.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;

/**
 * @author lee
 */
@SpringBootApplication
@EnableBinding(value = Processor.class)
@Slf4j
public class ProducerApplication implements CommandLineRunner {
    @Autowired
    @Qualifier(value = "output")
    private MessageChannel messageChannel;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        messageChannel.send(MessageBuilder.withPayload("xxx").build());
    }

    @Bean
    @InboundChannelAdapter(value = Processor.OUTPUT, poller = @Poller(fixedDelay = "3000", maxMessagesPerPoll = "1"))
    public MessageSource<String> timerMessageSource() {
        return () -> MessageBuilder.withPayload("短消息-" +new Student(20,"lee","male")).build();
    }

    @Data
    @AllArgsConstructor
    class Student {
        private Integer age;
        private String name;
        private String gender;
    }
}

