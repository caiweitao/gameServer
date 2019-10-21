package game.core.example.mongodb;

import game.core.db.mongodb.MongoUtils;

/**
* @author caiweitao
* @Date 2019年10月21日
* @Description 
*/
public class MongoMain {

	public static void main(String[] args) {
		MongoUtils.init();
		Cat cat = new Cat();
		cat.setName("hehe3");
		cat.setAge(12);
		MongoUtils.datastore.save(cat);
		
//		Cat cat2 = MongoUtils.datastore.get(Cat.class, "5dad4edd84207f42203dd1ea");
//		System.out.println(cat2);
	}
}
