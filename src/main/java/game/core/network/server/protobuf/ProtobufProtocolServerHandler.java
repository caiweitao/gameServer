package game.core.network.server.protobuf;

import java.io.Serializable;

import com.google.protobuf.MessageLite;

import game.core. network.cmd.Cmd.ClientCmdData;
import game.core.network.handler.ProtobufServerHandler;
import game.core.network.server.ProtocolSystem;
import game.core.network.session.ChannelSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
* @author caiweitao
* @Date 2019年9月23日
* @Description 
*/
public class ProtobufProtocolServerHandler extends SimpleChannelInboundHandler<ClientCmdData> {

	private int frequency;
	private long time;
	private int number;
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(ProtobufProtocolServerHandler.class);
	
	public ProtobufProtocolServerHandler() {
		this.frequency = ProtocolSystem.protocolServerConfig.getFrequencyLimit();
		this.time = 0L;
		this.number = 0;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void channelRead0(ChannelHandlerContext ctx, ClientCmdData cmdData) throws Exception {
		try {
			long currentTimeMillis = System.currentTimeMillis();
			long now = currentTimeMillis / 1000L;
			if (this.time == now) {
				++this.number;
			} else {
				this.number = 0;
				this.time = now;
			}

			if (this.frequency > this.number) {
				ProtobufServerHandler<?,? extends ChannelSession<? extends Serializable>,? extends MessageLite> cmdHandler 
				= (ProtobufServerHandler<?,? extends ChannelSession<? extends Serializable>,? extends MessageLite>) ProtocolSystem.serverHandlerMap.get(cmdData.getMessageId());
				
				if (cmdHandler != null) {
					cmdHandler.dispatchInit(currentTimeMillis, ctx, cmdData);
				} else {
					ProtocolSystem.systemHandler.outCommand(ctx, cmdData);
				}
			} else {
				ProtocolSystem.systemHandler.frequent(ctx, cmdData);
			}
		} catch (Exception var8) {
			logger.error("", var8);
		}
	}
	
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		ctx.close();
		super.channelUnregistered(ctx);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	}

	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
			ProtocolSystem.systemHandler.idle(ctx);
		}

		super.userEventTriggered(ctx, evt);
	}

}
