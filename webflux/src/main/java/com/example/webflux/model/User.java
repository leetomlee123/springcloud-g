package com.example.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author lee
 */
@Data
@AllArgsConstructor
@Document
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String name;
    private String phone;
    private Date birthday;
}
