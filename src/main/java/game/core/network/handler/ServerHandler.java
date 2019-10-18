package game.core.network.handler;

import java.io.Serializable;

import game.core.network.session.ChannelSession;
import io.netty.channel.ChannelHandlerContext;

/**
* @author caiweitao
* @Date 2019年9月24日
* @Description 
*/
public abstract class ServerHandler<Owner, Session extends ChannelSession<? extends Serializable>,D> {

	public abstract Owner getOwner(Session var1);//项目工程中实现,通关session，获取玩家
	
	public abstract void dispatch(int messageId,long time, ChannelHandlerContext ctx, D cmdData) throws Exception;
	
	public abstract boolean validate(int messageId,D cmdData,Owner owner,Session session) throws Exception;

	public abstract void handler (int messageId,D cmdData,Owner owner,Session session) throws Exception;
}
