package com.github.yuzhian.zero.boot.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 空闲检测
 *
 * @author yuzhian
 */
@Slf4j
public class WebSocketIdleStateHandler extends IdleStateHandler {
    private final long readerIdleTime;
    private static final TextWebSocketFrame maxIdleTimeResponse = new TextWebSocketFrame("{'code': 'MAXIMUM_IDLE_TIME'}");

    public WebSocketIdleStateHandler(long readerIdleTime) {
        super(readerIdleTime, 0, 0, TimeUnit.SECONDS);
        this.readerIdleTime = readerIdleTime;
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        if (log.isInfoEnabled()) log.info("No message within {} seconds, the connection is closed", readerIdleTime);
        ctx.channel().writeAndFlush(maxIdleTimeResponse);
        ctx.channel().close();
    }
}
