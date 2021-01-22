package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端启动, 指定线程模型 -> 指定 IO 模型 -> 连接读写处理逻辑 -> 绑定端口
 *
 * @author yuzhian
 */
@Slf4j
public class NettyServer {
    private static final int PORT = 8000;
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();              // 监听端口
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();            // 处理每一条连接的数据读写
        ServerBootstrap serverBootstrap = new ServerBootstrap();            // 引导类
        serverBootstrap
                .group(bossGroup, workerGroup)                              // 配置线程组, 构建线程模型
                .channel(NioServerSocketChannel.class)                      // 配置 IO 模型
                .childHandler(new ChannelInitializer<NioSocketChannel>() {  // 配置后续每条通道的初始化方式
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline()
                                .addLast(new LifeCycleTestHandler())
                                .addLast(new FirstServerHandler())
                        ;
                    }
                });
        serverBootstrap.bind(PORT);                                         // 绑定端口, 启动 netty 服务
        log.info("port({}): bound", PORT);
    }
}
