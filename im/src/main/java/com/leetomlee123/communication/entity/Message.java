package com.leetomlee123.communication.entity;

import java.io.Serializable;

public class Message implements Serializable {
    private Header header;
    private Object data;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

