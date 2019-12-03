package game.core.db.mongodb.dao;

import java.util.concurrent.atomic.AtomicInteger;

import game.core.db.mongodb.domain.UniqueModel;

/**
* @author caiweitao
* @Date 2019年12月3日
* @Description 
*/
public class InitUniqueModelDao<T extends UniqueModel<Integer>> extends UniqueModelDao<Integer, T> {
	
	private AtomicInteger idIndex;
	
	public InitUniqueModelDao(AtomicInteger idIndex) {
		this.idIndex = idIndex;
		initMaxId();
	}

	@Override
	public void initMaxId() {
		Integer maxId = (Integer)getMaxId();
		maxId = maxId != null ? maxId.intValue() + 1:1;
		int i = idIndex.get();
		maxId = maxId.intValue() > i? maxId.intValue():i;
		idIndex.set(maxId);
		System.out.println(getClass().getName() + "  maxId = " + maxId);
	}

	/**
	 * 获得下一个id 并自增
	 */
	@Override
	public Integer getAndIncrement() {
		return idIndex.getAndIncrement();
	}

}
