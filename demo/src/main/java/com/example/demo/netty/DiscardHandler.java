package com.example.demo.netty;

import com.alibaba.fastjson.JSON;
import com.example.demo.netty.chat.client.Student;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf by = (ByteBuf) msg;
        ctx.writeAndFlush(msg);
//        System.out.println(by.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getCause());
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        Student lee = new Student().setAge(21).setName("lee");
        StudentFrame studentFrame = new StudentFrame();
        byte[] bytes = JSON.toJSONBytes(lee);
        studentFrame.setLen(bytes.length);
        studentFrame.setMsg(bytes);
        final ChannelFuture channelFuture = ctx.writeAndFlush(studentFrame);
        channelFuture.addListener((ChannelFutureListener) f -> {
            assert f == channelFuture;
            ctx.close();
        });

    }
}
