package com.example.demo.netty.chat.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.*;

/**
 * @author lee
 */
@SuppressWarnings("ALL")
public class Client {
    public void run() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {

            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true).handler(new SimpleChatClientInitializer());
            Channel connect = null;
            try {
                connect = bootstrap.connect(InetAddress.getLocalHost(), 9898).sync().channel();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                connect.writeAndFlush(in.readLine() + "\r\n");
            }
//            connect.channel().closeFuture().sync();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
        singleThreadPool.shutdown();


    }
}
