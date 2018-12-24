package com.example.demo.netty.chat.server;

import com.example.demo.netty.chat.client.Student;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PoJoServerhandler extends SimpleChannelInboundHandler<Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Student student) throws Exception {
        student.setAge(11).setName("lee");
        System.out.println(student);
        channelHandlerContext.writeAndFlush(student).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
