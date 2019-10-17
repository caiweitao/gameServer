package game.core.code;

import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class MsgToJsonDecoder extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		try {
			JsonObject returnData = new JsonParser().parse(msg).getAsJsonObject();
			out.add(returnData);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

}
