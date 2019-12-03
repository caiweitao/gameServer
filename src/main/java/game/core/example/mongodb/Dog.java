package game.core.example.mongodb;

import org.mongodb.morphia.annotations.Entity;

import game.core.db.mongodb.domain.UniqueModel;

/**
* @author caiweitao
* @Date 2019年12月3日
* @Description 
*/
@Entity(value="Dog",noClassnameStored=true)//noClassnameStored=true 不会持久化className
public class Dog extends UniqueModel<Integer>{

	private String name;
    private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
