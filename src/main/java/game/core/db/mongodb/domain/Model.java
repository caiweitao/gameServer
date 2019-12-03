package game.core.db.mongodb.domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

/**
* @author caiweitao
* @Date 2019年12月3日
* @Description 
*/
public class Model {

	@Id
	private ObjectId _id;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
}
