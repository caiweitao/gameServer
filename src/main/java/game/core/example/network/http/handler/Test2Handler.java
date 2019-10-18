package game.core.example.network.http.handler;

import java.util.Map;

import game.core.network.server.http.HttpHandler;
import game.core.network.server.http.Response;


public class Test2Handler extends HttpHandler {

	@Override
	public Response handle(Map<String, Object> params) {
		defaultResponse.setContentType(Response.CONTENT_TYPE_HTML);
		defaultResponse.setContext("test2Handler。。。。。。");
		
		
		return defaultResponse;
	}

}
