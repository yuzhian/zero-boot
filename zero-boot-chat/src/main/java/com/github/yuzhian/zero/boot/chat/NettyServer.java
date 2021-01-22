package com.github.yuzhian.zero.boot.chat;

import com.github.yuzhian.zero.boot.chat.handler.IdleStateCheckHandler;
import com.github.yuzhian.zero.boot.chat.handler.PreTextWebSocketFrameHandler;
import com.github.yuzhian.zero.boot.chat.handler.TextWebSocketFrameHandler;
import com.github.yuzhian.zero.boot.properties.ChatProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author yuzhian
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyServer {
    private final ChatProperties chatProperties;
    private final PreTextWebSocketFrameHandler preTextWebSocketFrameHandler;
    private final TextWebSocketFrameHandler textWebSocketFrameHandler;

    private final NioEventLoopGroup boss = new NioEventLoopGroup();
    private final NioEventLoopGroup work = new NioEventLoopGroup();

    @PostConstruct
    public void afterPropertiesSet() {
        if (log.isInfoEnabled()) log.info("netty server starting...");
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, work);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) {
                ch.pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new HttpObjectAggregator(65536))
                        .addLast(new WebSocketServerProtocolHandler(chatProperties.getWebsocketPath()))
                        .addLast(new IdleStateCheckHandler(chatProperties.getMaxInactive()))
                        .addLast(preTextWebSocketFrameHandler)
                        .addLast(textWebSocketFrameHandler)
                ;
            }
        });
        serverBootstrap.bind(chatProperties.getPort());
        if (log.isInfoEnabled()) log.info("Netty server started on port(s): {} (im) with context path '{}'",
                chatProperties.getPort(), chatProperties.getWebsocketPath());
    }

    @PreDestroy
    public void destroy() throws Exception {
        if (log.isInfoEnabled()) log.info("netty server closing...");
        boss.shutdownGracefully().sync();
        work.shutdownGracefully().sync();
        if (log.isInfoEnabled()) log.info("netty server closed");
    }
}
