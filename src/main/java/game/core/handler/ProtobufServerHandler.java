package game.core.handler;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;

import game.core.cmd.Cmd.ClientCmdData;
import game.core.server.ProtocolSystem;
import game.core.session.ChannelSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * protobuf协议业务hanler基类
 * @author caiweitao
 * 2019年9月11日
 */
public abstract class ProtobufServerHandler<Owner, Session extends ChannelSession<? extends Serializable>,Req extends MessageLite> 
extends ServerHandler<Owner, Session,Req>{
	private MessageLite prototype;
	
	InternalLogger logger = InternalLoggerFactory.getInstance(this.getClass());
	
	public ProtobufServerHandler () {
		//获得超类的泛型参数的实际类型
		ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<Req> paraType = (Class) parameterizedType.getActualTypeArguments()[0];
		Method method = null;

		try {
			method = paraType.getMethod("getDefaultInstance");
			this.prototype = (MessageLite) method.invoke(paraType);
		} catch (NoSuchMethodException var12) {
			var12.printStackTrace();
		} catch (SecurityException var13) {
			var13.printStackTrace();
		} catch (IllegalAccessException var14) {
			var14.printStackTrace();
		} catch (IllegalArgumentException var15) {
			var15.printStackTrace();
		} catch (InvocationTargetException var16) {
			var16.printStackTrace();
		}
	}
	
	public void dispatchInit (long time, ChannelHandlerContext ctx, ClientCmdData cmdData) {
		MessageLite req = null;
		try {
			ByteString data = cmdData.getData();
			//获取与此消息类型相同的消息的解析器/再将数据解析为Req的消息
			req = (MessageLite) this.prototype.getParserForType().parseFrom(data);
			dispatch(cmdData.getMessageId(),time, ctx, (Req) req);
		} catch (Exception var11) {
			ProtocolSystem.systemHandler.error(cmdData, req, ctx, var11);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void dispatch(int messageId,long time, ChannelHandlerContext ctx, Req req) throws Exception {
		Session session = (Session) ProtocolSystem.sessionFactory.getSession(ctx.channel());
		if (session == null) {
			ChannelSession<? extends Serializable>  cs = ProtocolSystem.sessionFactory.newChannelSession(ctx.channel());
			ProtocolSystem.sessionFactory.register(cs);
			session = (Session)cs;
		}
		
		Owner owner = this.getOwner(session);
		if (validate(messageId,req,owner,session)) {
			handler(messageId,req,owner,session);
		}
	}
	
}
