package com.leetomlee123.communication.serialization;

public interface Serialization {

    <T> byte[] serialize(T obj);
    <T> T deSerialize(byte[] data,Class<T> clz);

}