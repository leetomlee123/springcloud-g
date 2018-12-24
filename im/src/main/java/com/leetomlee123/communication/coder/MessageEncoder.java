package com.leetomlee123.communication.coder;

import com.leetomlee123.communication.entity.Header;
import com.leetomlee123.communication.entity.Message;
import com.leetomlee123.communication.serialization.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author lee
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {
    private Class<?> clz;
    private Serialization serialization;

    public MessageEncoder(Class<?> clz, Serialization serialization) {
        this.clz = clz;
        this.serialization = serialization;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("the encode message is null");
        }

        Header header = msg.getHeader();
        int tag = header.getTag();
//        int commandCode = header.getCommandCode();
//        int version = header.getVersion();
        int length = header.getLength();
        Object data = msg.getData();
        out.writeInt(tag);
//        out.writeInt(commandCode);
//        out.writeInt(version);
        out.writeInt(0);
        if (data != null) {
            String str = (String) data;
            byte[] bytes = str.getBytes();
            length = bytes.length;
            out.writeBytes(bytes);
        }
        //写入length;
        out.setInt(12, length);
    }

}
