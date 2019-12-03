package game.core.example.mongodb;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
* @author caiweitao
* @Date 2019年10月21日
* @Description 
*/
@Entity(value="Cat",noClassnameStored=true)//noClassnameStored=true 不会持久化className
public class Cat {

	@Id
    private ObjectId id;
    private String name;
    private Integer age;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
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
	@Override
	public String toString() {
		return "Cat [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
    
}
