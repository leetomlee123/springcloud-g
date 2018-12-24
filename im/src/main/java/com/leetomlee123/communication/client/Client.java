package com.leetomlee123.communication.client;

import com.leetomlee123.communication.coder.MessageDecoder;
import com.leetomlee123.communication.entity.MessageRequest;
import com.leetomlee123.communication.serialization.JsonSerialization;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lee
 */
@SuppressWarnings("ALL")
public class Client {
    public void run() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {

            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new MessageDecoder(MessageRequest.class, new JsonSerialization()), new ClientHandler());
                }
            });
            ChannelFuture connect = null;
            try {
                connect = bootstrap.connect(InetAddress.getLocalHost(), 9898).sync();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            connect.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Client().run();
    }
}
