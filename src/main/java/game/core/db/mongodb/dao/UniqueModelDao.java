package game.core.db.mongodb.dao;

import org.mongodb.morphia.query.FindOptions;

import game.core.db.mongodb.domain.UniqueModel;

/**
 * @author caiweitao
 * @Date 2019年12月3日
 * @Description 
 */
public abstract class UniqueModelDao<E,T extends UniqueModel<E>> extends MongoDao<E, T> {

	public abstract void initMaxId();
	public abstract E getAndIncrement();
	
	/**
	 * 得到id最大值
	 * @return
	 */
	public E getMaxId() {
		FindOptions fo = new FindOptions();
		fo.limit(1);
		return ds.createQuery(clazz).order("-id").get(fo).getId();
	}
	
	public boolean insert (T t) {
		if (t != null) {
			t.setId(getAndIncrement());
		}
		ds.save(t);
		return true;
	}
	
}
