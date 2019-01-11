package com.leetomlee123.communication;

import com.leetomlee123.communication.server.ChatServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author lee
 */

@SpringBootApplication
public class ImApplication implements CommandLineRunner {

    @Value("${socket.port}")
    private int port;
    @Autowired
    private ChatServer chatServer;

    public static void main(String[] args) {
        SpringApplication.run(ImApplication.class);
    }

    @Override
    public void run(String... strings) {
        InetSocketAddress address = null;
        try {
            address = new InetSocketAddress(InetAddress.getLocalHost().getHostName(), port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ChannelFuture future = chatServer.run(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> chatServer.destroy()));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
