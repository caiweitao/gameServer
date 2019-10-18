package game.core.example.network.client.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;

import game.core.network.cmd.Cmd.ServerCmdData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
* @author caiweitao
* @Date 2019年9月24日
* @Description 
*/
public class ProtocolClientHandler extends SimpleChannelInboundHandler<ServerCmdData> {

	private final InternalLogger logger = InternalLoggerFactory.getInstance(ProtocolClientHandler.class);

	public ProtocolClientHandler() {
		super(false);
	}

	public void channelRead0(ChannelHandlerContext ctx, ServerCmdData cmdData) throws Exception {
		
		System.out.println(String.format("messageId[%s],hint[%s]", cmdData.getMessageId(),cmdData.getData().toString()));
		
//		ClientHandler<?> clientHandler = (ClientHandler) ProtocolSystem.clientHandlerMap.get(cmdData.getMessageId());
//		if (clientHandler != null) {
//			clientHandler.dispatch(ctx, cmdData);
//		} else {
//			this.logger.warn("没有找到action[" + cmdData.getMessageId() + "]");
//		}

	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	protected Object build(MessageLite prototype, ByteString data) {
		try {
			return prototype.getParserForType().parseFrom(data);
		} catch (Exception var4) {
			var4.printStackTrace();
			return null;
		}
	}

}
