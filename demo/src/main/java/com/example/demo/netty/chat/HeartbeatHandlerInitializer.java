package com.example.demo.netty.chat;

import com.example.demo.netty.chat.server.PoJoServerhandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class HeartbeatHandlerInitializer extends ChannelInitializer<Channel> {
    // 读超时
    private static final int READ_IDEL_TIME_OUT = 4;
    // 写超时
    private static final int WRITE_IDEL_TIME_OUT = 5;
    // 所有超时
    private static final int ALL_IDEL_TIME_OUT = 7;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT,
//                WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));

//        pipeline.addLast(new HttpServerCodec());
//        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
//        pipeline.addLast(new ChunkedWriteHandler());
//        pipeline.addLast(new HttpRequestHandler("/chat"));
//        pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));
//        pipeline.addLast(new TextWebSocketFrameHandler());
        pipeline.addLast(new PoJoServerhandler());
    }
}