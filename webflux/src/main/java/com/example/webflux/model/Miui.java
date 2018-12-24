package com.example.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author lee
 */
@Data
@AllArgsConstructor
@Document
public class Miui {




    private String author;

    private String integral;


    private String groupx;


    private String phoneType;


    private String miuiVersion;


    private String classify;


    private String title;


    private String viewNums;


    private String replies;


    private String content;

    private String imageFilePath;


    private Integer favorit;
    @Id
    private String url_object_code;
}