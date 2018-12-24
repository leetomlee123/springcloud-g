package com.example.demo.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class DiscardInitializer extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel ch) {

        ch.pipeline().addLast(new DiscardHandler()).addLast(new TimeEncode());

    }
}
