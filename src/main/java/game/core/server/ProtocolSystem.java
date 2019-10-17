package game.core.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import game.core.handler.ServerHandler;
import game.core.handler.SystemHandler;
import game.core.server.http.HttpServer;
import game.core.session.ChannelSession;
import game.core.session.DefaultSessionFactory;
import game.core.session.SessionFactory;

public class ProtocolSystem {
	
	public static ProtocolServer protocolServer;
	public static ProtocolServerConfig protocolServerConfig = new ProtocolServerConfig();//系统配置
	public static final Map<Integer, ServerHandler<?,?,?>> serverHandlerMap = new HashMap<>();//协议容器
	public static SessionFactory<? extends Serializable, ? extends ChannelSession<?>> sessionFactory = new DefaultSessionFactory();
	public static SystemHandler systemHandler;
	public static HttpServer httpServer;
	public static boolean isStoping = false;
	public static String messageIdName = "messageId";//协议号字段名，Json序列化的时候，设置跟客服端协议好的（协议号）字段名，一般设置为messageId/cmd/cmdId/code
	public static String httpMessageIdName = "code";//http协议号字段名
	/**
	 * 关闭通信端口 socket/http
	 */
	public static void shutdown() {
		isStoping = true;
		if (protocolServer != null) {
			try {
				protocolServer.shutdown();
			} catch (Exception var2) {
				var2.printStackTrace();
			}
		}

		if (httpServer != null) {
			try {
				httpServer.shutdown();
			} catch (Exception var1) {
				var1.printStackTrace();
			}
		}

	}
}
