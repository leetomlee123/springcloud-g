package com.leetomlee123.communication.entity;

import lombok.Data;

@Data
public class MessageRequest {
    private char infoType;
    private int dataLen;
    private byte data[];
}