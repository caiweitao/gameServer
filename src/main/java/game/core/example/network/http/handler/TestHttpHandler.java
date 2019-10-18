package game.core.example.network.http.handler;

import java.util.Map;

import com.google.gson.Gson;

import game.core.network.server.http.HttpHandler;
import game.core.network.server.http.Response;


public class TestHttpHandler extends HttpHandler {

	@Override
	public Response handle(Map<String, Object> params) {
		Response resp = new Response();
		Gson gson = new Gson();  
		String json = gson.toJson(params);
		resp.setContext(json);
		resp.setContentType(Response.CONTENT_TYPE_JSON);
		System.out.println("TestHttpHandler:"+params.toString());
//		for (Entry<String, Object> en :params.entrySet()) {
//			System.out.println(String.format("//%s=%s", en.getKey(),en.getValue()));
//		}
//		List<Integer> hehe = (List)params.get("hehe");
//		System.out.println("hehe:"+hehe);
//		System.out.println(params.get("oo"));
//		System.out.println("hehe".equals(params.get("xx")));
		return resp;
	}

}
