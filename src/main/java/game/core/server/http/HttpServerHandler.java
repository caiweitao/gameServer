package game.core.server.http;

import java.util.Iterator;
import java.util.Set;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import io.netty.util.CharsetUtil;

/**
 * http协议处理类
 * @author caiweitao
 * 2019年9月19日
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	
	private HttpConsole console;
	private FullHttpRequest request;
	
	public HttpServerHandler (HttpDisplay httpDisplay) {
		this.console = new HttpConsole(httpDisplay);
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
    	//100 Continue
    	if (HttpUtil.is100ContinueExpected(req)) {
    		ctx.write(new DefaultFullHttpResponse(
    				HttpVersion.HTTP_1_1,            
    				HttpResponseStatus.CONTINUE));
    	}
    	this.request = req;
    	
    	Response resp = this.console.console(req);
    	if (!this.writeResponse(req, ctx, resp)) {
			ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
			System.out.println("If keep-alive is off, close the connection once the content is fully written.");
		}
    }
    
    private boolean writeResponse(FullHttpRequest req, ChannelHandlerContext ctx, Response response) {
    	boolean keepAlive = HttpUtil.isKeepAlive(this.request);
		String context = response.getContext();
		FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
				req.decoderResult().isSuccess() ? HttpResponseStatus.OK : HttpResponseStatus.BAD_REQUEST,
				Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
		fullHttpResponse.headers().set("Content-Type", response.getContentType());
		fullHttpResponse.setStatus(response.getStatus());
		if (keepAlive) {
			fullHttpResponse.headers().set("Content-Length", fullHttpResponse.content().readableBytes());
			fullHttpResponse.headers().set("Connection", "keep-alive");
		}

		String cookieString = this.request.headers().get("Cookie");
		if (cookieString != null) {
			Set<Cookie> cookies = ServerCookieDecoder.LAX.decode(cookieString);
			if (!cookies.isEmpty()) {
				Iterator<Cookie> var10 = cookies.iterator();

				while (var10.hasNext()) {
					Cookie cookie = (Cookie) var10.next();
					fullHttpResponse.headers().add("Set-Cookie", ServerCookieEncoder.LAX.encode(cookie));
				}
			}
		} else {
			fullHttpResponse.headers().add("Set-Cookie", ServerCookieEncoder.LAX.encode("key1", "value1"));
			fullHttpResponse.headers().add("Set-Cookie", ServerCookieEncoder.LAX.encode("key2", "value2"));
		}

//		ctx.write(fullHttpResponse);
		ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
		return keepAlive;
	}
    
}
