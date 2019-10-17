package game.core.handler;


import io.netty.channel.ChannelHandlerContext;

public abstract class SystemHandler<D> {
	
	public abstract void offline(ChannelHandlerContext paramChannelHandlerContext);
	
	/**协议不存在*/
	public abstract void outCommand(ChannelHandlerContext paramChannelHandlerContext, D paramClientCmdData);
	/**请求过于频繁*/
	public abstract void frequent(ChannelHandlerContext paramChannelHandlerContext, D paramClientCmdData);
	
	public abstract void error(D paramClientCmdData, Object paramObject, ChannelHandlerContext paramChannelHandlerContext, Exception paramException);
	/**太久没收到客户端消息做处理*/
	public abstract void idle(ChannelHandlerContext var1);
	
//	public void timeoutWarning(ChannelHandlerContext ctx, int messageId, Class<?> handlerClass, MessageLite req, MessageLite appendReq, long time) {
//		logger.warn("运行效率警告: 命令[" + messageId + "]\n处理类[" + handlerClass + "]\n命令信息[" + req + req.getClass().getSimpleName() + "]\n附加信息[" + appendReq + 
//				"]\n=========>响应时间超过了[" + time / 1000.0D + "]秒");
//	}
}
