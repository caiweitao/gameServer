package game.core.server;


import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import game.core.cmd.ServerCmdInitializer;
import game.core.common.option.OptionDescription;
import game.core.common.option.OptionMap;
import game.core.handler.SystemHandler;
import game.core.session.ChannelSession;
import game.core.session.SessionFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author caiweitao
 * @Date 2019年9月20日
 * @Description
 */
public class ProtocolServer {
	
	private Channel channel;
	private ServerBootstrap bootstrap;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	public void start(int port) {
		InternalLogger logger = InternalLoggerFactory.getInstance(getClass());
		if (ProtocolSystem.systemHandler == null) {
			logger.error("systemHandler不能为空,请给systemHandler赋值");
			System.exit(0);
		} else if (ProtocolSystem.sessionFactory == null) {
			logger.error("会话工厂没有部署,请添部署会话工厂");
			System.exit(0);
		} else if (ProtocolSystem.serverHandlerMap.isEmpty()) {
			logger.error("命令部署不能为空,请添加命令配置");
			System.exit(0);
		} else {
			//Reactor线程组
			//1用于服务端接受客户端的连接
			bossGroup = new NioEventLoopGroup(ProtocolSystem.protocolServerConfig.getBossGroupNumber(), new DefaultThreadFactory("bossGroup", 10));//可以在参数中声明线程个数，默认个数是内核数*2
			//2用于进行SocketChannel的网络读写
			workerGroup = new NioEventLoopGroup(ProtocolSystem.protocolServerConfig.getWorkerGroupNumber(), new DefaultThreadFactory("workerGroup",10));
			try {
				//Netty用于启动NIO服务器的辅助启动类
				bootstrap = new ServerBootstrap();
				//将两个NIO线程组传入辅助启动类中
				bootstrap.group(bossGroup, workerGroup)
				//设置创建的Channel为NioServerSocketChannel类型
				.channel(NioServerSocketChannel.class)
				//配置NioServerSocketChannel的TCP参数
				.option(ChannelOption.SO_BACKLOG, ProtocolSystem.protocolServerConfig.getNettyOptionSoBacklog());
				//设置绑定IO事件的处理类
				bootstrap.childHandler(ProtocolSystem.protocolServerConfig.getChannelInitializer());
				//绑定端口，同步等待成功（sync()：同步阻塞方法）
				//ChannelFuture主要用于异步操作的通知回调
				ChannelFuture channelFuture = null;
				channelFuture = bootstrap.bind(port).sync();
				channel = channelFuture.channel();
				ProtocolSystem.protocolServer = this;
				logger.info(getChannelOptionString());
				logger.info(String.format("端口[%s]启动成功>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>",port));
				//等待服务端监听端口关闭
				//					channel.closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			finally {
//				//优雅退出，释放线程池资源
//				bossGroup.shutdownGracefully();
//				workerGroup.shutdownGracefully();
//			}
		}
    }
	
	public boolean shutdown() {
		System.out.println("关闭ProtocolServer");
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
	
	public String getChannelOptionString() {
		StringBuilder sb = new StringBuilder("==========netty Channel 启动参数选项=======================\r");
		StringBuilder warn = new StringBuilder();
		Map<ChannelOption<?>, Object> options = this.channel.config().getOptions();
		Iterator<?> var8 = options.entrySet().iterator();

		while (var8.hasNext()) {
			Entry<ChannelOption<?>, Object> e = (Entry) var8.next();
			String keyDesc = null;
			String valueDesc = null;
			OptionDescription optionItem = (OptionDescription) OptionMap.optionMap.get(e.getKey());
			if (optionItem != null) {
				keyDesc = optionItem.getTitle();
				valueDesc = optionItem.getDescription(e.getValue().toString());
			}

			keyDesc = e.getKey() + (keyDesc == null ? "" : "[" + keyDesc + "]");
			valueDesc = e.getValue() + (valueDesc == null ? "" : "[" + valueDesc + "]");
			sb.append(keyDesc + " = " + valueDesc + "\r");
		}

		sb.append("==========netty Channel 启动参数选项 end=======================\r");
		if (warn.length() > 0) {
			sb.append("警告:" + warn.toString());
		}

		return sb.toString();
	}

	
	public void setFrequencyLimit(int frequencyLimit) {
		ProtocolSystem.protocolServerConfig.setFrequencyLimit(frequencyLimit);
	}
	
	public void setIdleReaderSeconds(int idleReaderSeconds) {
		ProtocolSystem.protocolServerConfig.setIdleReaderSeconds(idleReaderSeconds);
	}
	
	public void setWarningTime(long warningTime) {
		ProtocolSystem.protocolServerConfig.setWarningTime(warningTime);
	}
	
	public void setWorkerGroupNumber(int workerGroupNumber) {
		ProtocolSystem.protocolServerConfig.setWorkerGroupNumber(workerGroupNumber);
	}
	
	public void setNettyOptionSoBacklog(int nettyOptionSoBacklog) {
		ProtocolSystem.protocolServerConfig.setNettyOptionSoBacklog(nettyOptionSoBacklog);
	}
	
	public void setChannelInitializer(ChannelInitializer<?> channelInitializer) {
		ProtocolSystem.protocolServerConfig.setChannelInitializer(channelInitializer);
	}
	
	public void setMessageIdName (String messageIdName) {
		ProtocolSystem.messageIdName = messageIdName;
	}
	
	public void addCmdInitializer(ServerCmdInitializer serverCmdInitializer) {
		serverCmdInitializer.init();
	}
	
	public void setSystemHandler(SystemHandler handler) {
		ProtocolSystem.systemHandler = handler;
	}
	
	public void setSessionFactory(
			SessionFactory<? extends Serializable, ? extends ChannelSession<?>> sessionFactory) {
		ProtocolSystem.sessionFactory = sessionFactory;
	}
	
	public static void main(String[] args) throws InterruptedException {
		new ProtocolServer().start(3036);
	}
}
