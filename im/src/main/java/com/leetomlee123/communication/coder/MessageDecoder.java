package com.leetomlee123.communication.coder;


import com.leetomlee123.communication.entity.Header;
import com.leetomlee123.communication.entity.Message;
import com.leetomlee123.communication.serialization.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {
    private Class<?> clz;
    private Serialization serialization;

    public MessageDecoder(Class<?> clz, Serialization serialization) {
        this.clz = clz;
        this.serialization = serialization;
    }

    private int MIN_LENGTH = 4 + 4 ;
//    private int MIN_LENGTH = 4 + 4 + 4 + 4;
    private int TAG = 1;
    private int MAX = 2048;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() <= MIN_LENGTH) {
            return;
        }
        if (in.readableBytes() > MAX) {
            in.skipBytes(in.readableBytes());
        }
        Message message = new Message();
        int tag = 0;
        while (true) {
            in.markReaderIndex();
            if ((tag = in.readInt()) == TAG) {
                break;
            }
            in.resetReaderIndex();
            in.readByte();
            if (in.readableBytes() <= MIN_LENGTH) {
                return;
            }
        }
        int commandCode = in.readInt();
        int version = in.readInt();
        int length = in.readInt();
        Header header = new Header();
//        header.setCommandCode(commandCode);
        header.setLength(length);
        header.setTag(tag);
//        header.setVersion(version);
        message.setHeader(header);
        byte[] bytes = new byte[length];
        if (length > 0) {
            if (in.readableBytes() < length) {
                //包还没齐，返回
                in.resetReaderIndex();
                return;
            }
//            System.out.println("bytebuffer可读的范围" + in.readableBytes());
//            if(in.readableBytes() > length){
//                in.resetReaderIndex();
//                System.out.println("返回了");
//                return;
//            }
            in.readBytes(bytes);
            String data = new String(bytes);
            message.setData(data);
        }
        out.add(message);

    }


}
