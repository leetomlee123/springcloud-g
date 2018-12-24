package com.example.webflux.web.controller;

import com.example.webflux.dao.MiuiRepository;
import com.example.webflux.model.Miui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author lee
 */
@RequestMapping(value = "/miuis")
@RestController
public class MiuiController {
    @Autowired
    @Qualifier(value = "miuiRepository")
    private MiuiRepository miuiRepository;
    @GetMapping(path = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Miui> getAll() {
        return miuiRepository.findBy();
    }
}
