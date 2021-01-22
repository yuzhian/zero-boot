package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yuzhian
 */
@Slf4j
public class NettyClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)                                         // 指定线程模型
                .channel(NioSocketChannel.class)                            // 配置 IO 模型
                .handler(new ChannelInitializer<SocketChannel>() {          // 配置 IO 处理逻辑
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });
        bootstrap.connect(HOST, PORT)                                       // 建立连接
                .addListener(future -> log.info("[client] connected {}: ({})", future.isSuccess(), PORT));
    }
}
