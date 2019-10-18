package game.core.network.cmd;

import java.io.Serializable;
import java.lang.reflect.Field;

import game.core.network.handler.ServerHandler;
import game.core.network.server.ProtocolSystem;
import game.core.network.session.ChannelSession;

/**
* @author caiweitao
* @Date 2019年9月24日
* @Description 
*/
public abstract class ServerCmdInitializer {
	
	public abstract void init();
	protected abstract void putCmd (int cmdId,ServerHandler<?, ? extends ChannelSession<? extends Serializable>,?> serverHandler);
	
	protected void load(Class<?> gameCmdDefine) {
		boolean r = this.fetchDefine(gameCmdDefine);
		if (!r) {
			System.err.println("handler有问题请处理");
			System.exit(0);
		}
	}
	
	private boolean fetchDefine (Class<?> gameCmdDefine) {
		Field[] declaredFields = gameCmdDefine.getDeclaredFields();
		boolean result = true;
		for (Field field : declaredFields) {
			CmdDefine annotation = field.getAnnotation(CmdDefine.class);
			if (annotation != null) {
				try {
					int messageId = field.getInt(null);
					Class<?> handler = annotation.handler();
					if (ProtocolSystem.serverHandlerMap.containsKey(messageId)) {
//						LogManager.getLogger(getClass()).error(field.getName() + "=" + messageId + ",重叠messageId");
						System.out.println(field.getName() + "=" + messageId + ",重叠messageId");
						result = false;
					}
					ServerHandler<?, ? extends ChannelSession<? extends Serializable>,?> newInstance = (ServerHandler<?,?,?>) handler.newInstance();
					if (!ServerHandler.class.isAssignableFrom(handler)) {
						System.out.println(String.format("类[%s]请继承ServerHandler", handler.getSimpleName()));
						result = false;
					}
					
					putCmd(messageId, newInstance);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
