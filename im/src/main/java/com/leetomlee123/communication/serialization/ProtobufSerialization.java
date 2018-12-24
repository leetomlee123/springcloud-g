package com.leetomlee123.communication.serialization;

public class ProtobufSerialization implements Serialization {


    @Override
    public <T> byte[] serialize(T obj) {
        return new byte[0];
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> clz) {
        return null;
    }
}
