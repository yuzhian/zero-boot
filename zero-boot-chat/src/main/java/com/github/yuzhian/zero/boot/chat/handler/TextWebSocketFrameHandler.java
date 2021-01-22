package com.github.yuzhian.zero.boot.chat.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 消息处理
 *
 * @author yuzhian
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        if (log.isDebugEnabled()) log.debug("server receives the message: {}", msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("Received and return: " + msg.text()));
        if (log.isDebugEnabled()) log.debug("all messages have been returned");
    }
}
