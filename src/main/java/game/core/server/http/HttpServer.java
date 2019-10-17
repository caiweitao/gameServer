package game.core.server.http;

import game.core.server.ProtocolSystem;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * HTTP 协议Server类
 * @author caiweitao
 * 2019年9月18日
 */
public class HttpServer {
	InternalLogger logger = InternalLoggerFactory.getInstance(getClass());
	private HttpDisplay httpDisplay;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Channel channel;
	
	/**
	 * 传入handler声明对象，类里面用注解HttpDefine声明，eg:
	 * @HttpDefine(desc = "test", handler = TestHttpHandler.class)
	 * public static final int TEST = 100;
	 * @param clazz
	 */
	public void setHttpDefine(Class<?> clazz) {
		this.httpDisplay = new HttpDisplay(clazz);
	}

	public void start(int port) {
		if (this.httpDisplay == null) {
			logger.error("没有设置HttpDefine");
			System.exit(0);
		}
		this.httpDisplay.load();//加载http定义好的规则
		this.bossGroup = new NioEventLoopGroup(ProtocolSystem.protocolServerConfig.getHttpBossGroupNumber(),
				new DefaultThreadFactory("httpBossGroup", 10));
		this.workerGroup = new NioEventLoopGroup(ProtocolSystem.protocolServerConfig.getHttpWorkerGroupNumber(),
				new DefaultThreadFactory("httpWorkerGroup", 10));

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.group(this.bossGroup, this.workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new HttpServerInitializer(this.httpDisplay));
			this.channel = b.bind(port).sync().channel();
			ProtocolSystem.httpServer = this;
			logger.info(String.format("HTTP端口[%s]启动成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>",port));
		} catch (Exception var3) {
			var3.printStackTrace();
		}
	}
	
	public boolean shutdown() {
		logger.info("关闭HttpServer>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if (channel != null) {
			try {
				channel.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (bossGroup != null) {
			try {
				bossGroup.shutdownGracefully();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (workerGroup != null) {
			try {
				workerGroup.shutdownGracefully();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	
	public void setHttpMessageIdName (String httpMessageIdName) {
		ProtocolSystem.httpMessageIdName = httpMessageIdName;
	}
}
