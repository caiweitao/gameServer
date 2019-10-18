package game.core.network.server.json;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import game.core.network.handler.JsonServerHandler;
import game.core.network.server.ProtocolSystem;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class JsonProtocolServerHandler extends SimpleChannelInboundHandler<JsonObject> {

	private static final InternalLogger logger = InternalLoggerFactory.getInstance(JsonProtocolServerHandler.class);
	private int frequency = ProtocolSystem.protocolServerConfig.getFrequencyLimit();
	private long time = 0L;
	private int number = 0;
	
	@Override
	@SuppressWarnings("unchecked")
	protected void channelRead0(ChannelHandlerContext ctx, JsonObject msg) throws Exception {
		try {
			long currentTimeMillis = System.currentTimeMillis();
			long now = currentTimeMillis / 1000L;
			if (time == now) {
				number += 1;
			} else {
				number = 0;
				time = now;
			}
			if (frequency > number) {
				JsonElement cmd = msg.get(ProtocolSystem.messageIdName);//协议号key
				if (cmd == null) {
					ProtocolSystem.systemHandler.outCommand(ctx, msg);
				} else {
					JsonServerHandler<?,?> cmdHandler = (JsonServerHandler<?, ?>) ProtocolSystem.serverHandlerMap.get(cmd.getAsInt());
					if (cmdHandler != null) {
						cmdHandler.dispatch(cmd.getAsInt(),currentTimeMillis, ctx, msg);
					} else {
						ProtocolSystem.systemHandler.outCommand(ctx, msg);
					}
				}
			} else {
				ProtocolSystem.systemHandler.frequent(ctx, msg);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("连接的客户端地址:" + ctx.channel().remoteAddress());
		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ProtocolSystem.systemHandler.offline(ctx);
		ProtocolSystem.sessionFactory.unbind(ctx.channel());
		ctx.close();
		ctx.channel().close();
		super.channelInactive(ctx);
		logger.debug("检测到连接断开:" + ctx.channel());
	}

}
