package game.core.db.mongodb.dao;

import java.lang.reflect.ParameterizedType;

import org.mongodb.morphia.Datastore;

import game.core.db.mongodb.MongoUtils;

/**
* @author caiweitao
* @Date 2019年10月21日
* @Description 
*/
public class MongoDao<K,T> {
	
	// T 实体类型
	protected Class<T> clazz;
	protected Datastore ds = MongoUtils.datastore;
	
	public MongoDao() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获取第一个类型参数的真实类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	public boolean insert (T t) {
		ds.save(t);
		return true;
	}
	
//	public boolean update (Query query,UpdateOperations uo) {
//		
//	}
	
}
