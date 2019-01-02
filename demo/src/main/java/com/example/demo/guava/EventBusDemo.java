package com.example.demo.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class EventBusDemo {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus("lee");
        eventBus.register(new EventLister());
        while (true) {

            eventBus.post(new Event(String.valueOf(System.currentTimeMillis())));
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Event {
    private String msg;

}

class EventLister {
    @Subscribe
    public void listen(Event event) {
        System.out.println(event.getMsg());
    }
}
