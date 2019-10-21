package game.core.db.mongodb;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

/**
* @author caiweitao
* @Date 2019年10月21日
* @Description 
*/
public class MongoUtils {
	
	public static Datastore datastore;

	public static void init () {
		Morphia morphia = new Morphia();

		// 告诉Morphia在哪里找到你的类
		morphia.mapPackage("game.core.example.mongodb");

		//创建datastore，并连接到指定数据库
		//datastore有两个参数，第一个用来连接到MongoDB，第二个是数据库的名字。
		datastore = morphia.createDatastore(new MongoClient("127.0.0.1",27017), "cwt_test");
//		datastore.ensureIndexes();
	}

}
