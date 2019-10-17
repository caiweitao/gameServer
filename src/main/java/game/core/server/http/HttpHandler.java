package game.core.server.http;

import java.util.Map;

public abstract class HttpHandler {

	protected Response defaultResponse = new Response();

	public abstract Response handle(Map<String, Object> params);
}
