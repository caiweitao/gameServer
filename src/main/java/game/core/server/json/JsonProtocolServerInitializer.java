package game.core.server.json;

import game.core.code.MsgToJsonDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Json协议-IO处理类初始化
 * @author caiweitao
 * 2019年9月11日
 */
public class JsonProtocolServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new StringDecoder());//bytes to String
		p.addLast(new MsgToJsonDecoder());//String to JsonObject
//		p.addLast("encode", new StringEncoder());
        p.addLast(new JsonProtocolServerHandler());
	}
}
