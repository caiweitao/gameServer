package game.core.example.network.socket;

import game.core.network.cmd.Cmd.ClientCmdData;
import game.core.network.handler.SystemHandler;
import io.netty.channel.ChannelHandlerContext;

public class ProtobufGameSystemHandler extends SystemHandler<ClientCmdData> {

	@Override
	public void offline(ChannelHandlerContext paramChannelHandlerContext) {
		System.out.println("玩家下线【%s】================"+paramChannelHandlerContext.channel());
		GameSessionFactory.factory.unbind(paramChannelHandlerContext.channel());
	}

	@Override
	public void outCommand(ChannelHandlerContext paramChannelHandlerContext, ClientCmdData paramClientCmdData) {
		System.out.println("命令找不到===========================" + paramClientCmdData.toString());
		
	}

	@Override
	public void frequent(ChannelHandlerContext paramChannelHandlerContext, ClientCmdData paramClientCmdData) {
		System.out.println("请求过于频繁");
		
	}

	@Override
	public void error(ClientCmdData paramClientCmdData, Object paramObject,
			ChannelHandlerContext paramChannelHandlerContext, Exception paramException) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void idle(ChannelHandlerContext ctx) {
		System.out.println("玩家太久没消息，将其连接断线...");
		ctx.channel().close();
		ctx.close();
		
	}

}
