package game.core.network.server;

import game.core.network.server.protobuf.ProtobufProtocolServerInitializer;
import io.netty.channel.ChannelInitializer;

public class ProtocolServerConfig {

	private final int bossGroupNumber = 1;
	private final int httpBossGroupNumber = 1;
	private int workerGroupNumber = 0;
	private int httpWorkerGroupNumber = 2;
	private int frequencyLimit = 100;//1秒内请求次数限制
	private long warningTime = 1000L;//毫秒
	private int idleReaderSeconds = 600;
	private final int idleWriterSeconds = 0;
	private final int idleAllSeconds = 0;
	private int nettyOptionSoBacklog = 1024;//BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
	private ChannelInitializer<?> channelInitializer = new ProtobufProtocolServerInitializer();
	
	public int getWorkerGroupNumber() {
		return workerGroupNumber;
	}
	public void setWorkerGroupNumber(int workerGroupNumber) {
		this.workerGroupNumber = workerGroupNumber;
	}
	public int getHttpWorkerGroupNumber() {
		return httpWorkerGroupNumber;
	}
	public void setHttpWorkerGroupNumber(int httpWorkerGroupNumber) {
		this.httpWorkerGroupNumber = httpWorkerGroupNumber;
	}
	public int getFrequencyLimit() {
		return frequencyLimit;
	}
	public void setFrequencyLimit(int frequencyLimit) {
		this.frequencyLimit = frequencyLimit;
	}
	public long getWarningTime() {
		return warningTime;
	}
	public void setWarningTime(long warningTime) {
		this.warningTime = warningTime;
	}
	public int getIdleReaderSeconds() {
		return idleReaderSeconds;
	}
	public void setIdleReaderSeconds(int idleReaderSeconds) {
		this.idleReaderSeconds = idleReaderSeconds;
	}
	public int getBossGroupNumber() {
		return bossGroupNumber;
	}
	public int getHttpBossGroupNumber() {
		return httpBossGroupNumber;
	}
	public int getNettyOptionSoBacklog() {
		return nettyOptionSoBacklog;
	}
	public void setNettyOptionSoBacklog(int nettyOptionSoBacklog) {
		this.nettyOptionSoBacklog = nettyOptionSoBacklog;
	}
	public ChannelInitializer<?> getChannelInitializer() {
		return channelInitializer;
	}
	public void setChannelInitializer(ChannelInitializer<?> channelInitializer) {
		this.channelInitializer = channelInitializer;
	}
	public int getIdleWriterSeconds() {
		return idleWriterSeconds;
	}
	public int getIdleAllSeconds() {
		return idleAllSeconds;
	}
	
	
}
