package game.core.network.cmd;

import java.io.Serializable;

import game.core.network.handler.ServerHandler;
import game.core.network.server.ProtocolSystem;
import game.core.network.session.ChannelSession;

/**
 * protobuf协议初始化基类-项目中实现该类并在init方法中调用 putCmd 方法添加项目协议到系统
 * @author caiweitao
 * 2019年9月11日
 */
public abstract class ProtobufServerCmdInitializer extends ServerCmdInitializer{

	@Override
	protected void putCmd(int cmdId,ServerHandler<?, ? extends ChannelSession<? extends Serializable>,?> serverHandler) {
			ProtocolSystem.serverHandlerMap.put(cmdId, serverHandler);
	}

}
