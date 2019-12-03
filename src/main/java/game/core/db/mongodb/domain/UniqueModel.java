package game.core.db.mongodb.domain;

/**
* @author caiweitao
* @Date 2019年12月3日
* @Description 
*/
public class UniqueModel<E> extends Model {

	private E id;

	public E getId() {
		return id;
	}

	public void setId(E id) {
		this.id = id;
	}
	
}
