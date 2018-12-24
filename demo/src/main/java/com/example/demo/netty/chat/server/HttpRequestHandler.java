package com.example.demo.netty.chat.server;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * @author lee
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> { //1
    private final String wsUri;


    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (wsUri.equalsIgnoreCase(request.uri())) {
            ctx.fireChannelRead(request.retain());
        } else {
            if (HttpHeaders.is100ContinueExpected(request)) {
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
                response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
                response.headers().set(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
                response.headers().set(ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT,DELETE");
                ctx.writeAndFlush(response);
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}