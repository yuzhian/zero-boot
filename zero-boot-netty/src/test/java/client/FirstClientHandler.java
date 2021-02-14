package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author yuzhian
 */
@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer().writeBytes("hello,world".getBytes(StandardCharsets.UTF_8));
        ctx.channel().writeAndFlush(buffer);
        log.info("[client] sent");
    }
}
