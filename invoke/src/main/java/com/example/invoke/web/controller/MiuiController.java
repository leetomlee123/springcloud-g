package com.example.invoke.web.controller;


import com.example.invoke.dto.CommonDto;
import com.example.invoke.model.Miui;
import com.example.invoke.service.IMiuiService;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/miuis")
public class MiuiController {
    @Autowired
    @Qualifier(value = "miuiImpl")
    private IMiuiService miuiService;


    @GetMapping(value = "/{limit}/{size}")
    public ResponseEntity getMiuis(@PathVariable(value = "limit") Integer limit, @PathVariable(value = "size") Integer size) {
        ResponseEntity<PageInfo<Miui>> pageInfoResponseEntity = new ResponseEntity<>(miuiService.getLimitMiui(limit, size), HttpStatus.OK);
        return pageInfoResponseEntity;
    }

    @GetMapping(value = "/top")
    public ResponseEntity top() {
        List<CommonDto> commonDtos = miuiService.topAuthors();
        List<CommonDto> commonDtos1 = miuiService.topPhoneTypes();
        ArrayList<Object> objects = Lists.newArrayList();
        objects.add(commonDtos);
        objects.add(commonDtos1);
        return ResponseEntity.ok(objects);
    }
}
