package game.core.example.network.client.json;

import java.net.InetSocketAddress;

import com.google.gson.JsonObject;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {
	private final String host;
    private final int port;

    public Client() {
        this(0);
    }

    public Client(int port) {
        this("localhost", port);
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    System.out.println("connected...");
                                    ch.pipeline().addLast("decoder", new StringDecoder());
                                    ch.pipeline().addLast("encoder", new StringEncoder());
                                    ch.pipeline().addLast(new ClientHandler());
                                }
                            });
            ChannelFuture cf = b.connect().sync(); // 异步连接服务器
            System.out.println("connected..."); // 连接完成
            
            JsonObject jo = new JsonObject();
            jo.addProperty("playerId", 1);
            jo.addProperty("cmdId", 1001);
            cf.channel().writeAndFlush(Unpooled.copiedBuffer(jo.toString().getBytes()));//"{\"xx\":\"abccc\"}"Unpooled.copiedBuffer(jo.toString().getBytes())
            System.out.println("send..."+jo.toString()); // 连接完成
            cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
            System.out.println("closed.."); // 关闭完成
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }
    }

    public static void main(String[] args) throws Exception {
        new Client("127.0.0.1", 3060).start(); // 连接127.0.0.1/65535，并启动
    }
}
