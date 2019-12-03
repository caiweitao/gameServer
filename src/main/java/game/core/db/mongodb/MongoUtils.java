package game.core.db.mongodb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

/**
* @author caiweitao
* @Date 2019年10月21日
* @Description  mongo 工具类，Morphia开发简介可查看https://www.cnblogs.com/ssjf/p/11466310.html
*/
public class MongoUtils {
	
	public static Datastore datastore;
	public final static MongoConfig config = new MongoConfig();
	
	public static void init (String properties) {
		initConfig(properties);
		initMongo(config);
	}

	/**
	 * 初始化mongo
	 * @param properties properties配置文件
	 */
	private static void initConfig (String properties) {
		// 配置文件参数
		Properties p = new Properties();
        try {
			p.load(new FileInputStream(properties));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
        
        String mapPackage = p.getProperty("mongodb.mapPackage");
        String host = p.getProperty("mongodb.host");
        String port = p.getProperty("mongodb.port");
        String dbName = p.getProperty("mongodb.dbName");
        
        config.setMapPackage(mapPackage);
        config.setHost(host);
        config.setPort(Integer.parseInt(port));
        config.setDbName(dbName);
        
	}
	
	private static void initMongo (MongoConfig config) {
		Morphia morphia = new Morphia();

		// 告诉Morphia在哪里找到你的类
		morphia.mapPackage(config.getMapPackage());

		// 创建datastore，并连接到指定数据库
		// datastore有两个参数，第一个用来连接到MongoDB，第二个是数据库的名字。
		datastore = morphia.createDatastore(new MongoClient(config.getHost(),config.getPort()), 
				config.getDbName());//,config.getUsername(),config.getPassword()
		datastore.ensureIndexes();
	}

}
