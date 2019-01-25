package com.example.book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lx
 * @Date: 2019/1/24 15:01
 */
@ControllerAdvice(annotations = RestController.class)
public class AllExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity reCall(Exception e) {
        return ResponseEntity.ok("error");
    }
}
