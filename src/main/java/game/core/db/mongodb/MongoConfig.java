package game.core.db.mongodb;
/**
* @author caiweitao
* @Date 2019年10月21日
* @Description 
*/
public class MongoConfig {

	private String host;
	private int port;
	private String dbName;
	private String username;
	private String password;
	private int minConnectionsPerHost;
	private int maxConnectionsPerHost = 10;
	private int threadsAllowedToBlockForConnectionMultiplier = 5;
	private int connectTimeout = 10000;
	private int maxWaitTime = 120000;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMinConnectionsPerHost() {
		return minConnectionsPerHost;
	}
	public void setMinConnectionsPerHost(int minConnectionsPerHost) {
		this.minConnectionsPerHost = minConnectionsPerHost;
	}
	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}
	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}
	public int getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}
	public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public int getMaxWaitTime() {
		return maxWaitTime;
	}
	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
	
	
}
