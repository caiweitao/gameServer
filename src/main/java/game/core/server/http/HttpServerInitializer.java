package game.core.server.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private HttpDisplay httpDisplay;

	public HttpServerInitializer(HttpDisplay httpDisplay) {
		this.httpDisplay = httpDisplay;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		// 请求解码器
        pipeline.addLast("http-decoder", new HttpRequestDecoder());
        // 将HTTP消息的多个部分合成一条完整的HTTP消息,65535为接收的最大contentlength
        pipeline.addLast("http-aggregator", new HttpObjectAggregator(65535));
        // 响应转码器
        pipeline.addLast("http-encoder", new HttpResponseEncoder());
        // 解决大码流的问题，ChunkedWriteHandler：向客户端发送HTML5文件
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        // 自定义处理handler
		pipeline.addLast(new HttpServerHandler(this.httpDisplay));// 请求处理器
	}

}
