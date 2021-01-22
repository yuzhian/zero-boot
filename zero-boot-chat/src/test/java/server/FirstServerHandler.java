package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author yuzhian
 */
@Slf4j
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("[server] received: {}", ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
        ByteBuf outBuf = ctx.alloc().buffer().writeBytes("hello world from server".getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(outBuf);
        log.info("[server] sent");
    }
}
