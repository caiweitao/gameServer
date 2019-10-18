package game.core.network.server.http;

import io.netty.handler.codec.http.HttpResponseStatus;

public class Response {

	public static final transient String CONTENT_TYPE_HTML = "text/html; charset=UTF-8";
	public static final transient String CONTENT_TYPE_PLAIN = "text/plain; charset=UTF-8";
	public static final transient String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
	private transient String contentType = "text/html; charset=UTF-8";
	private HttpResponseStatus status;
	private String context;

	public Response() {
		this.status = HttpResponseStatus.OK;
		this.context = "";
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public HttpResponseStatus getStatus() {
		return this.status;
	}

	public void setStatus(HttpResponseStatus status) {
		this.status = status;
	}

	public String getContext() {
		return this.context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
