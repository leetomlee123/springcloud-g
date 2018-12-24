package com.example.demo.netty;

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
                    socketChannel.pipeline().addLast( new ClientHandler());
                }
            });
            ChannelFuture connect = null;
            try {
                connect = bootstrap.connect(InetAddress.getLocalHost(), 9998).sync();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            connect.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//                .setNameFormat("demo-pool-%d").build();
//        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//
//        singleThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
//        singleThreadPool.shutdown();
        new Thread(() -> {

            try {
                new Client().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
