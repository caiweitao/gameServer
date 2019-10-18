package game.core.network.session;

import java.io.Serializable;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;

import game.core. network.cmd.Cmd.ServerCmdData;
import game.core. network.cmd.Cmd.ServerCmdData.Builder;
import game.core.network.server.ProtocolSystem;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public abstract class ChannelSession<T extends Serializable>{

	private T owner;//玩家 Id
	protected Channel channel;
	
	public ChannelSession(Channel parent) {
		this.channel = parent;
	}
	
	public ChannelSession(T owner,Channel parent) {
		this.channel = parent;
		this.owner = owner;
	}

	public T getOwner() {
		return owner;
	}

	public void setOwner(T owner) {
		this.owner = owner;
	}
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void response(JsonObject data) {
		this.channel.writeAndFlush(Unpooled.copiedBuffer(data.toString().getBytes()));
	}
	
	public void response (int messageId,GeneratedMessageV3.Builder<?> rspMsg) {
		Builder cmdData = ServerCmdData.newBuilder();
		cmdData.setData(rspMsg.build().toByteString());
		cmdData.setMessageId(messageId);
		cmdData.setResult(true);
		this.channel.writeAndFlush(cmdData);
	}

	protected void jsonError(int messageId, String tips) {
		JsonObject error = new JsonObject();
		error.addProperty(ProtocolSystem.messageIdName, messageId);
		error.addProperty("tips", tips);
		this.channel.writeAndFlush(error);
	}
	
	protected void protoError (int messageId, String tips) {
		Builder cmdData = ServerCmdData.newBuilder();
		cmdData.setMessageId(messageId);
		cmdData.setResult(false);
		cmdData.setData(ByteString.copyFromUtf8(tips));
		this.channel.writeAndFlush(cmdData);
	}
}
