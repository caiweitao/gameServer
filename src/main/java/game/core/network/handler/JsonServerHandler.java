package game.core.network.handler;

import java.io.Serializable;

import com.google.gson.JsonObject;

import game.core.network.server.ProtocolSystem;
import game.core.network.session.ChannelSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Json协议业务hanler基类
 * @author caiweitao
 * 2019年9月11日
 */
public abstract class JsonServerHandler<Owner, Session extends ChannelSession<? extends Serializable>> extends ServerHandler<Owner, Session,JsonObject>{
	InternalLogger logger = InternalLoggerFactory.getInstance(this.getClass());

	@SuppressWarnings("unchecked")
	public void dispatch(int messageId,long time, ChannelHandlerContext ctx, JsonObject cmdData) throws Exception {
		Session session = (Session) ProtocolSystem.sessionFactory.getSession(ctx.channel());
		if (session == null) {
			ChannelSession<? extends Serializable>  cs = ProtocolSystem.sessionFactory.newChannelSession(ctx.channel());
			ProtocolSystem.sessionFactory.register(cs);
			session = (Session)cs;
		}
		
		Owner owner = this.getOwner(session);
		if (validate(messageId,cmdData,owner,session)) {
			handler(messageId,cmdData,owner,session);
		}
	}
	
}
