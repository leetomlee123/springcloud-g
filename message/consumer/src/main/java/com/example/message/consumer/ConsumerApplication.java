package com.example.message.consumer;

import com.leetomlee.cloud.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

/**
 * @author lee
 */
@SpringBootApplication
@EnableBinding(value = Processor.class)
@Slf4j
public class ConsumerApplication {


    public static void main(String[] args)
    {

        SpringApplication.run(ConsumerApplication.class, args);
    }

    @StreamListener(value = Sink.INPUT)
    public void getMsg(Message<String> msg) {
        log.info("receive message:" + msg.getPayload());
    }
}

