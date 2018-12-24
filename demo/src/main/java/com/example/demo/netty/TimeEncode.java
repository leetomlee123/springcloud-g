package com.example.demo.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TimeEncode extends MessageToByteEncoder<StudentFrame> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, StudentFrame student, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(JSON.toJSONBytes(student));
    }
}
