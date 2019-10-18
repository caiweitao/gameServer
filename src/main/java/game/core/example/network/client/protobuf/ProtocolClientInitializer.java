package game.core.example.network.client.protobuf;

import game.core.network.cmd.Cmd.ServerCmdData;
import game.core.network.code.ProtobufFrameDecoder;
import game.core.network.code.ProtobufFrameEncoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
* @author caiweitao
* @Date 2019年9月24日
* @Description 
*/
public class ProtocolClientInitializer extends ChannelInitializer<SocketChannel> {

	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new ChannelHandler[]{new ProtobufFrameDecoder(),
				new ProtobufDecoder(ServerCmdData.getDefaultInstance()), new ProtobufFrameEncoder(),
				new ProtobufEncoder(), new ProtocolClientHandler()});
	}

}
