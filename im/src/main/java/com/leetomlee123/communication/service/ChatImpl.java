package com.leetomlee123.communication.service;

import com.leetomlee123.communication.api.IChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service(value = "chat")
public class ChatImpl implements IChat {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void get() {

    }
}
