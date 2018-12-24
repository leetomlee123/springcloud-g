package com.leetomlee123.communication.serialization;

import com.alibaba.fastjson.JSON;

public class JsonSerialization implements Serialization {
    @Override
    public <T> byte[] serialize(T obj) {
        if (obj != null) {
            return JSON.toJSONBytes(obj);
        }
        return null;
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> clz) {
        if (data != null) {
            return JSON.parseObject(data, clz);
        }
        return null;
    }
}
