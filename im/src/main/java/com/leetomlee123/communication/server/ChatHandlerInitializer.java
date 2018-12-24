package com.leetomlee123.communication.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.web.HttpRequestHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author lee
 */
public class ChatHandlerInitializer extends ChannelInitializer<SocketChannel> {
    // 读超时
    private static final int READ_IDEL_TIME_OUT = 8;
    // 写超时
    private static final int WRITE_IDEL_TIME_OUT = 10;
    // 所有超时
    private static final int ALL_IDEL_TIME_OUT = 14;

    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
                WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new ImageHandler());
    }
}
