package com.leetomlee123.communication.entity;

public class WebRequest {
    public String sender;
    public Object content;
    public boolean isBroadcast;
    public Integer type;
    public String id;
    public String contentType;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public WebRequest() {
    }

    public WebRequest(String sender, Object content, boolean isBroadcast, Integer type) {
        this.sender = sender;
        this.content = content;
        this.isBroadcast = isBroadcast;
        this.type = type;
    }

    public WebRequest(Object content, Integer type) {
        this.content = content;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public boolean isBroadcast() {
        return isBroadcast;
    }

    public void setBroadcast(boolean broadcast) {
        isBroadcast = broadcast;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
