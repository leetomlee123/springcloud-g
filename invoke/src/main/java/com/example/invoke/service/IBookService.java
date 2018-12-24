package com.example.invoke.service;

import com.example.invoke.model.Bbb;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Cacheable(keyGenerator = "keyGenerator", value = "books")
public interface IBookService {


    List<Bbb> getPageBook();

    List<String> getCategory();

    Bbb getBookById(Integer id);


    PageInfo<Bbb> getCategoryById(String cid, Integer limit, Integer size);
}
