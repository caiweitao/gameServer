package game.core.server.http;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HttpDisplay {

	private Class<?> clazz;
	private Map<Integer, HttpHandler> map = new HashMap<>();
	
	public HttpDisplay(Class<?> clazz) {
		this.clazz = clazz;
	}

	public HttpHandler getHttpHandler(int code) {
		return (HttpHandler) this.map.get(code);
	}

	public void putHttpHandler (int code, HttpHandler handler) {
		this.map.put(code, handler);
	}

	public boolean load () {
		Field[] declaredFields = clazz.getDeclaredFields();
		boolean result = true;
		for (Field field : declaredFields) {
			HttpDefine annotation = field.getAnnotation(HttpDefine.class);
			if (annotation != null) {
				try {
					int messageId = field.getInt(null);
					Class<?> handler = annotation.handler();
					if (map.containsKey(messageId)) {
//						LogManager.getLogger(getClass()).error(field.getName() + "=" + messageId + ",重叠messageId");
						System.out.println(field.getName() + "=" + messageId + ",重叠messageId");
						result = false;
					}
					HttpHandler newInstance = (HttpHandler) handler.newInstance();
					if (!HttpHandler.class.isAssignableFrom(handler)) {
						System.out.println(String.format("类[%s]请继承HttpHandler", handler.getSimpleName()));
						result = false;
					}
					
					putHttpHandler(messageId, newInstance);
					
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
