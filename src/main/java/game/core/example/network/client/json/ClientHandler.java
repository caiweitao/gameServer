package game.core.example.network.client.json;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//		System.out.println("client channelRead..");
//		ByteBuf buf = msg.readBytes(msg.readableBytes());
//		System.out.println("Client received:" + ByteBufUtil.hexDump(buf) + "; The value is:" + buf.toString(Charset.forName("utf-8")));
		System.out.println("clien handle:"+msg);

	}

}
