package com.example.invoke.web.controller;

import com.example.invoke.model.Bbb;
import com.example.invoke.serviceimpl.BookImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lee
 */
@RestController
@RequestMapping(value = "/books")
public class BookController {
    @Qualifier(value = "bookImpl")
    @Autowired
    private BookImpl bookImpl;

    @GetMapping(value = "/index")
    public ResponseEntity getIndexPageBook() {
        List<Bbb> pageBook = bookImpl.getPageBook();
        return new ResponseEntity(pageBook, HttpStatus.OK);
    }

    @GetMapping(value = "/category")
    public ResponseEntity getCategory() {
        return new ResponseEntity(bookImpl.getCategory(), HttpStatus.OK);
    }

    @GetMapping(value = "/category/{cid}/{limit}/{size}")
    public ResponseEntity getCategoryByid(@PathVariable(value = "cid") String cid, @PathVariable(value = "limit") Integer limit, @PathVariable(value = "size") Integer size) {
        return new ResponseEntity(bookImpl.getCategoryById(cid, limit, size), HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity getBookById(@PathVariable(value = "id") Integer id) {
        Bbb bookById = bookImpl.getBookById(id);
        return new ResponseEntity(bookById, HttpStatus.OK);
    }
}
