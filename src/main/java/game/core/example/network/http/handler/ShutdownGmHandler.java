package game.core.example.network.http.handler;

import java.util.Map;

import game.core.network.server.ProtocolSystem;
import game.core.network.server.http.HttpHandler;
import game.core.network.server.http.Response;


public class ShutdownGmHandler extends HttpHandler {

	@Override
	public Response handle(Map<String, Object> params) {
		ProtocolSystem.shutdown();
		defaultResponse.setContext("关闭通信端口。。。");
		return defaultResponse;
	}

}
