package game.core.server.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import game.core.common.json.MapTypeAdapter;
import game.core.server.ProtocolSystem;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;

/**
 * 解析、控制类
 * @author caiweitao
 * 2019年9月19日
 */
public class HttpConsole {
	
	private HttpDisplay httpDisplay;
	
	public HttpConsole(HttpDisplay httpDisplay) {
		this.httpDisplay = httpDisplay;
	}

	public Response console(FullHttpRequest fullHttpRequest) {
		Map<String, Object> params = parse(fullHttpRequest);
		Object code = params.get(ProtocolSystem.messageIdName);
		if (code == null) {
			return NotFound404();
		}
		
		int codeNum = -1;
		try{
			if (code instanceof Long) {
				codeNum = ((Long)code).intValue();
			} else {
				codeNum = Integer.parseInt(String.valueOf(code));
			}
		} catch (Exception e) {
			return NotFound404();
		}
		
		HttpHandler httpHandler = this.httpDisplay.getHttpHandler(codeNum);
		if (httpHandler == null) {
			return NotFound404();
		}
		return httpHandler.handle(params);
	}
	
	/**
     * 处理请求参数
     * @param fullHttpRequest
     * @return
     */
    private Map<String, Object> parse (FullHttpRequest fullHttpRequest) {
    	Map<String, Object> params = new HashMap<String, Object>();

    	if (fullHttpRequest.method() == HttpMethod.GET) {// 处理GET请求
    		QueryStringDecoder decoder = new QueryStringDecoder(fullHttpRequest.uri());
    		Map<String, List<String>> paramList = decoder.parameters();
    		for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
    			params.put(entry.getKey(), entry.getValue().get(0));
    		}
    		return params;

    	} else if (fullHttpRequest.method() == HttpMethod.POST) {// 处理POST请求
    		//获取uri后携带的参数
    		QueryStringDecoder queryDecoder = new QueryStringDecoder(fullHttpRequest.uri());
    		Map<String, List<String>> paramList = queryDecoder.parameters();
    		for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
    			params.put(entry.getKey(), entry.getValue().get(0));
    		}

    		String strContentType = fullHttpRequest.headers().get("Content-Type");
    		if (strContentType == null) {
    			return params;
    		} else if (strContentType.trim().contains("x-www-form-urlencoded")) {//表单
    			HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), fullHttpRequest);
    			List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();

    			for (InterfaceHttpData data : postData) {
    				if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
    					MemoryAttribute attribute = (MemoryAttribute) data;
    					params.put(attribute.getName(), attribute.getValue());
    				}
    			}
    			return params;
    		} else if (strContentType.trim().contains("application/json")) {//json
    			try {
    				ByteBuf content = fullHttpRequest.content();
    				byte[] reqContent = new byte[content.readableBytes()];
    				content.readBytes(reqContent);
    				String strContent = new String(reqContent, "UTF-8");
    				
    				Gson gson = new GsonBuilder()
    						//重写map的反序列化
    		                .registerTypeAdapter(new TypeToken<Map<String, Object>>() {}.getType(),
    		                		new MapTypeAdapter()).create();

    				Map<String, Object> temp = gson.fromJson(strContent, new TypeToken<Map<String, Object>>() {}.getType());
    				params.putAll(temp);
    				
    				return params;
    			} catch (UnsupportedEncodingException e) {
    				return null;
    			}
    		}
    	}
    	return params;
    }
    
    /**
     * 404
     * @return
     */
    private Response NotFound404 () {
    	Response response404 = new Response();
		response404.setStatus(HttpResponseStatus.NOT_FOUND);
		response404.setContext("Not Found 404 =_=!!");
		return response404;
    }
    
}
