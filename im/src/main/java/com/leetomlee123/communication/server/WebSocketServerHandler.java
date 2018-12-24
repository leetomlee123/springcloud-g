package com.leetomlee123.communication.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.leetomlee123.communication.constant.MessageType;
import com.leetomlee123.communication.entity.OnlineUser;
import com.leetomlee123.communication.entity.Ping;
import com.leetomlee123.communication.entity.WebRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * @author lee
 */
@Component
@Slf4j
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private int loss_connect_time = 0;

    private static WebSocketServerHandler handler;
    private WebSocketServerHandshaker handshaker;
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public ByteBuf buffer;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        handler = this;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        // 传统的HTTP接入
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }
        // WebSocket接入
        else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("welcome to join");
    }

    private void handleHttpRequest(ChannelHandlerContext ctx,
                                   FullHttpRequest req) throws Exception {

        // 如果HTTP解码失败，返回HHTP异常
        if (!req.decoderResult().isSuccess()
                || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1,
                    BAD_REQUEST));
            return;
        }

        // 构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:9998/chat", null, false, 1024 * 1024 * 10);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory
                    .sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx,
                                      WebSocketFrame frame) {

        // 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(),
                    (CloseWebSocketFrame) frame.retain());
            return;
        }

        // 判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 判断是否是ContinuationWebSocketFrame消息
        if (frame instanceof ContinuationWebSocketFrame) {
            ContinuationWebSocketFrame continuationWebSocketFrame = (ContinuationWebSocketFrame) frame;
            initBuffer();
            buffer.writeBytes(continuationWebSocketFrame.content());
            if (continuationWebSocketFrame.isFinalFragment()) {

            } else {
                return;
            }
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (frame instanceof BinaryWebSocketFrame) {

        }

//        // 返回应答消息
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame msg = (TextWebSocketFrame) frame;
            initBuffer();
            buffer.writeBytes(frame.content());
            if (msg.isFinalFragment()) {

            } else {
                return;
            }
        }
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.headers().set(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
        response.headers().set(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT,DELETE");
        ctx.write(response);

        String sc = buffer.toString(CharsetUtil.UTF_8);
        log.info(sc);
        buffer.release();
        if ("6".equals(JSON.parseObject(sc).get("type").toString())) {
            loss_connect_time = 0;
            log.info("back");
            return;
        }



        String sender = JSON.parseObject(sc).get("sender").toString();

        Channel incoming = ctx.channel();
        String id = incoming.id().asLongText();
        handler.redisTemplate.opsForValue().set("olur_" + sender, id);
        if (!StringUtils.isEmpty(JSON.parseObject(sc).get("content"))) {
            if (Integer.valueOf(JSON.parseObject(sc).get("type").toString()) == 5) {
                for (Channel channel : channels) {
                    if (channel.id().asLongText().equals(JSON.parseObject(sc).get("id").toString())) {
                        WebRequest webRequest = JSON.parseObject(sc, WebRequest.class);
                        webRequest.setId(id);
                        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSON(webRequest).toString()));
                    }
                }
            } else {
                channels.writeAndFlush(new TextWebSocketFrame(sc));
            }
        } else {
            onlineUsers();
        }
    }

    private void outFile(ChannelHandlerContext ctx) {
        File file = new File("C:\\Users\\lee\\Desktop\\file\\images");

        for (File f : file.listFiles()
        ) {
            byte[] base64 = new byte[1024 * 1024 * 10];
            try {
                Files.write(Paths.get(f.getPath()), Base64.getDecoder().decode(base64), StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ctx.writeAndFlush(base64);
        }
    }

    public void initBuffer() {
        if (buffer == null) {
            buffer = Unpooled.buffer(64 * 1024);
        } else {

            if (buffer.refCnt() == 0) {
                buffer = Unpooled.buffer(64 * 1024);
            } else {
                if (buffer.writableBytes() < 64 * 1024) {
                    buffer = buffer.capacity(buffer.capacity() + 64 * 1024);
                } else {

                }
            }
        }
    }

    private void onlineUsers() {
        Iterator iterator = handler.redisTemplate.keys("olur_*").iterator();
        ArrayList<OnlineUser> users = Lists.newArrayList();

        while (iterator.hasNext()) {
            String key = String.valueOf(iterator.next());
            String id = String.valueOf(handler.redisTemplate.opsForValue().get(key));
            List<String> collect = channels.stream().map(Channel::id).map(ChannelId::asLongText).collect(Collectors.toList());
            if (collect.contains(id)) {
                users.add(new OnlineUser(id, key.split("_")[1]));
            } else {
                handler.redisTemplate.delete(key);
            }
        }
        WebRequest webRequest = new WebRequest(users, MessageType.USERS.getIndex());
        channels.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(webRequest)));
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, FullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();
        ctx.writeAndFlush("welcome to join");
//        channels.add(incoming);
        System.out.println("Client:" + incoming.remoteAddress() + "加入");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();
//        Iterator iterator = handler.redisTemplate.keys("olur_*").iterator();
//        while (iterator.hasNext()) {
//            String key = String.valueOf(iterator.next());
//            if (String.valueOf(handler.redisTemplate.opsForValue().get(key)).equals(incoming.id().asLongText())) {
//                handler.redisTemplate.delete(key);
//            }
//        }
//        onlineUsers();
        System.out.println("Client:" + incoming.remoteAddress() + "离开");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
//        Ping ping = new Ping(MessageType.PING.getName(), MessageType.PING.getIndex());
//        ctx.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(ping)));
        System.out.println("Client:" + incoming.remoteAddress() + "在线");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        incoming.writeAndFlush("welcome to join");
//        Iterator iterator = handler.redisTemplate.keys("olur_*").iterator();
//        while (iterator.hasNext()) {
//            String key = String.valueOf(iterator.next());
//            if (String.valueOf(handler.redisTemplate.opsForValue().get(key)).equals(incoming.id().asLongText())) {
//
//                handler.redisTemplate.delete(key);
//            }
//        }
//        onlineUsers();
        System.out.println("Client:" + incoming.remoteAddress() + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
//        Iterator iterator = handler.redisTemplate.keys("olur_*").iterator();
//        while (iterator.hasNext()) {
//            String key = String.valueOf(iterator.next());
//            if (String.valueOf(handler.redisTemplate.opsForValue().get(key)).equals(incoming.id().asLongText())) {
//
//                handler.redisTemplate.delete(key);
//            }
//        }
        System.out.println(cause.getMessage());
        System.out.println("Client:" + incoming.remoteAddress() + "异常");
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Ping ping = new Ping(MessageType.PING.getName(), MessageType.PING.getIndex());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                loss_connect_time++;
                if (loss_connect_time > 2) {
                    Channel incoming = ctx.channel();
//                    Iterator iterator = handler.redisTemplate.keys("olur_*").iterator();
//                    while (iterator.hasNext()) {
//                        String key = String.valueOf(iterator.next());
//                        if (String.valueOf(handler.redisTemplate.opsForValue().get(key)).equals(incoming.id().asLongText())) {
//
//                            handler.redisTemplate.delete(key);
//                        }
//                    }
                    incoming.close();
                } else {
                    ctx.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(ping)))
                            .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                }
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
