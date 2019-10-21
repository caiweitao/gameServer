package game.core.db.mongodb;

import java.lang.reflect.ParameterizedType;

import org.mongodb.morphia.query.Query;

/**
* @author caiweitao
* @Date 2019年10月21日
* @Description 
*/
public class MongoDao<K,T> {
	
	protected Class<T> clazz;
	
	public MongoDao() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获取第一个类型参数的真实类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[1];
	}

	public void add (T t) {
		MongoUtils.datastore.save(t);
	}
	
	public void get (K k) {
		Query<T> query = MongoUtils.datastore.createQuery(clazz);
		
	}
}
