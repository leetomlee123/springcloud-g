package com.example.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author lee
 */
public class TimeDecode extends ByteToMessageDecoder {



    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readByte() < 4) {
            return;
        }
        int i = byteBuf.readInt();
        if (byteBuf.readByte() < i) {
            return;
        }
        ByteBuf byteBuf2 = byteBuf.readBytes(i);
        list.add(byteBuf2);


    }
}
