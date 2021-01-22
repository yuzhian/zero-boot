package com.github.yuzhian.zero.boot.chat.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.yuzhian.zero.boot.context.ObjectMapperHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 鉴权
 * 第一次请求时鉴权. 鉴权成功: 移除当前操作类; 鉴权失败: 关闭通道.
 * websocket onopen -> token -> login ? remove this : close
 *
 * @author yuzhian
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class PreTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final SessionRepository<? extends Session> repository;
    @Value("${zero.security.name}")
    private String authKey;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws JsonProcessingException {
        try {
            // not login
            Session session = repository.findById((String) (ObjectMapperHolder.readValue(msg.text(), Map.class)).get(authKey));
            if (session == null || session.isExpired()) {
                ctx.channel().writeAndFlush(new TextWebSocketFrame(ObjectMapperHolder.writeValueAsString(Map.of("code", "NEED_UNEXPIRED_TOKEN"))));
                ctx.channel().close();
                return;
            }
            // logged
            ctx.channel().writeAndFlush(new TextWebSocketFrame(ObjectMapperHolder.writeValueAsString(Map.of("code", "LOGIN_SUCCESS"))));
            ctx.pipeline().remove(this);
        } catch (JsonProcessingException ignored) {
            // format failed
            ctx.channel().writeAndFlush(new TextWebSocketFrame(ObjectMapperHolder.writeValueAsString(Map.of("code", "NOT_JSON"))));
            ctx.channel().close();
        }
    }
}
