package game.core.example.mongodb;

import java.io.File;

import game.core.db.mongodb.MongoUtils;

/**
* @author caiweitao
* @Date 2019年10月21日
* @Description 
*/
public class MongoMain {

	public static void main(String[] args) {
		String root = new File(MongoMain.class.getResource("/").getFile()).getPath() + "/";
		MongoUtils.init(root+"config/mongodb.properties");
		
//		Cat cat = new Cat();
//		cat.setName("nnnnnn");
//		cat.setAge(121);
//		MongoUtils.datastore.save(cat);
		
//		Cat cat1 = MongoUtils.datastore.get(Cat.class, "5de4d01384207f14247a6fc4");
//		System.out.println(cat1);
//		CatDao dao = new CatDao();
//		Cat cat2 = dao.getCatByName("xxn");
//		System.out.println(cat2);
//		cat2.setAge(10);
//		dao.update(cat2);
//		cat2 = dao.getCatByName("xxn");
//		System.out.println(cat2);
//		dao.deleteByName("xxn");
//		cat2 = dao.getCatByName("xxn");
//		System.out.println(cat2);
		
		DogDao ddao = new DogDao();
		for (int i=0;i<10;i++) {
			Dog dog = new Dog();
			dog.setName("xiaoming4");
			dog.setAge(2);
			ddao.insert(dog);
		}
		
		System.out.println(ddao.getMaxId());
	}
}
