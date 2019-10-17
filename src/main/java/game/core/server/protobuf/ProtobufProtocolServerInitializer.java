package game.core.server.protobuf;

import game.core.cmd.Cmd.ClientCmdData;
import game.core.code.ProtobufFrameDecoder;
import game.core.code.ProtobufFrameEncoder;
import game.core.server.ProtocolSystem;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
* @author caiweitao
* @Date 2019年9月23日
* @Description 
*/
public class ProtobufProtocolServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new ChannelHandler[]{new ProtobufFrameDecoder(),
				new ProtobufDecoder(ClientCmdData.getDefaultInstance()), new ProtobufFrameEncoder(),
				new ProtobufEncoder(),
				//心跳、跟客户端太久没来往做出处理
				new IdleStateHandler(ProtocolSystem.protocolServerConfig.getIdleReaderSeconds(),
						ProtocolSystem.protocolServerConfig.getIdleWriterSeconds(),
						ProtocolSystem.protocolServerConfig.getIdleAllSeconds()),
				new ProtobufProtocolServerHandler()
				});
		
	}

}
