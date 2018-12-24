package com.example.demo.netty.chat;

//public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {
//    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
//            .unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
//                    CharsetUtil.UTF_8));
//
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent event = (IdleStateEvent) evt;
//            String type = "";
//            if (event.state() == IdleState.READER_IDLE) {
//                type = "read idle";
//            } else if (event.state() == IdleState.WRITER_IDLE) {
//                type = "write idle";
//            } else if (event.state() == IdleState.ALL_IDLE) {
//                type = "all idle";
//            }
//
//            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(
//                    ChannelFutureListener.CLOSE_ON_FAILURE);
//
//            System.out.println(ctx.channel().remoteAddress() + "超时类型：" + type);
//        } else {
//            super.userEventTriggered(ctx, evt);
//        }
//    }
//}
