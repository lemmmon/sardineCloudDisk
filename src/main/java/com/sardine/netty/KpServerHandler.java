package com.sardine.netty;

import com.sardine.common.thread.HeartbeatMonitor;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: rickiyang
 * @date: 2020/3/15
 * @description:
 */
@ChannelHandler.Sharable
@Component
public class KpServerHandler extends SimpleChannelInboundHandler {
    @Autowired
    private HeartbeatMonitor heartbeatMonitor;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        if (message != null && message.contains("@{")) {
            System.out.println("get message:" + message);
            heartbeatMonitor.resetTimes(message);
            ctx.write("heartbeat-server");
            ctx.flush();
        }
    }


    /**
     * 如果5s没有读请求，则向客户端发送心跳
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.READER_IDLE.equals((event.state()))) {
                ctx.writeAndFlush("heartbeat").addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
