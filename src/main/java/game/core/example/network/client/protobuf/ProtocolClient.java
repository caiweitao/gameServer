package game.core.example.network.client.protobuf;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.MessageLite;

import game.core.example.network.cmd.Test.TestReq;
import game.core.network.cmd.Cmd.ClientCmdData;
import game.core.network.server.ProtocolSystem;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
* @author caiweitao
* @Date 2019年9月24日
* @Description 
*/
public class ProtocolClient {

	private Channel channel;
	private EventLoopGroup group = new NioEventLoopGroup();

	public ProtocolClient(String host, int port) {
		try {
			Bootstrap b = new Bootstrap();
			((Bootstrap) ((Bootstrap) b.group(this.group)).channel(NioSocketChannel.class))
					.handler(new ProtocolClientInitializer());
			this.channel = b.connect(host, port).sync().channel();
			
			TestReq.Builder req = TestReq.newBuilder();
			req.setName("小明");
			req.setAge(18);
			write(1001, req);
		} catch (Exception var4) {
			var4.printStackTrace();
		}

	}

	public void write(int messageId, GeneratedMessageV3.Builder<?> builder) {
		ClientCmdData.Builder msg = ClientCmdData.newBuilder();
		msg.setMessageId(messageId);
		msg.setData(builder.build().toByteString());
		this.channel.writeAndFlush(msg.build());
	}

	public void close() {
		if (this.group != null) {
			try {
				this.group.shutdownGracefully();
			} catch (Exception var3) {
				var3.printStackTrace();
			}
		}

		if (this.channel != null) {
			try {
				this.channel.close();
			} catch (Exception var2) {
				var2.printStackTrace();
			}
		}

	}

	public Channel getChannel() {
		return this.channel;
	}

//	public void addClientHandler(int messageId, ClientHandler<? extends MessageLite> clientHandler) {
//		ProtocolSystem.clientHandlerMap.put(messageId, clientHandler);
//	}
	
	public static void main(String[] args) {
		new ProtocolClient("127.0.0.1", 3060);
	}
}
