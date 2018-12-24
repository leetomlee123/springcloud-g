package com.leetomlee123.communication.entity;

public class OnlineUser {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public OnlineUser() {
    }

    public OnlineUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
