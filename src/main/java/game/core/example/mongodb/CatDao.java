package game.core.example.mongodb;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import game.core.db.mongodb.dao.MongoDao;

/**
* @author caiweitao
* @Date 2019年12月3日
* @Description 
*/
public class CatDao extends MongoDao<String, Cat> {
	
	public Cat getCatByName (String name) {
		return ds.createQuery(Cat.class).field("name").equal(name).get();
	}

	public void update (Cat cat) {
		Query<Cat> query = ds.createQuery(Cat.class).field("name").equal(cat.getName());
		UpdateOperations<Cat> update = ds.createUpdateOperations(Cat.class).set("age",cat.getAge());
		ds.update(query, update);
	}
	
	public void deleteByName (String name) {
		Query<Cat> query = ds.createQuery(Cat.class).field("name").equal("xxn");
		ds.delete(query);
	}
}
