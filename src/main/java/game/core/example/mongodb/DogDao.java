package game.core.example.mongodb;

import java.util.concurrent.atomic.AtomicInteger;

import game.core.db.mongodb.dao.InitUniqueModelDao;

/**
* @author caiweitao
* @Date 2019年12月3日
* @Description 
*/
public class DogDao extends InitUniqueModelDao<Dog> {
	
	private static final AtomicInteger index = new AtomicInteger(0);

	public DogDao() {
		super(index);
	}

}
