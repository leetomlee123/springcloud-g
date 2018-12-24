package com.example.invoke.cron;

import com.example.invoke.serviceimpl.MovieImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lee
 */
@Component
@Slf4j
public class TopRateMovie {
    @Qualifier(value = "movieImpl")
    @Autowired
    private MovieImpl movieImpl;

    @Scheduled(cron = "0 30 7 * * ?")
    public void pushDataScheduled() {
        log.info("start push data scheduled!");
        log.info("end push data scheduled!");
    }

}
