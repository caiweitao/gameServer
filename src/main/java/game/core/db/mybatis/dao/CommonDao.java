package game.core.db.mybatis.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import game.core.db.mybatis.CommonMapper;
import game.core.db.mybatis.MybatisDB;

/**
* @author caiweitao
* @Date 2019年10月12日
* @Description 
*/
@SuppressWarnings("unchecked")
public abstract class CommonDao<K,T,M> {//k主键，T实体类，Mapper
	
	public T get (K k) {
		try (SqlSession sqlSession = getSqlSession()) {
			CommonMapper<K,T> mapper = (CommonMapper<K, T>) sqlSession.getMapper(getM());
			return mapper.get(k, getT());
		}
	}
	
//	public List<T> getListByKey () {
//		try (SqlSession sqlSession = getSqlSession()) {
//			CommonMapper<K,T> mapper = (CommonMapper<K, T>) sqlSession.getMapper(getM());
//			return mapper.getListByKey(key, getT());
//		}
//	}
	
	public long count () {
		try (SqlSession sqlSession = getSqlSession()) {
			CommonMapper<K,T> mapper = (CommonMapper<K, T>) sqlSession.getMapper(getM());
			return mapper.count(getT());
		}
	}
	
	public int add (T t) {
		if (t == null) {
			return 0;
		}
		try (SqlSession sqlSession = getSqlSession()) {
			CommonMapper<K,T> mapper = (CommonMapper<K, T>) sqlSession.getMapper(getM());
			return mapper.insert(t);
		}
	}
	
	public int batchAdd (List<T> tList) {
		if (tList == null || tList.size() <= 0) {
			return 0;
		}
		try (SqlSession sqlSession = getSqlSession()) {
			CommonMapper<K,T> mapper = (CommonMapper<K, T>) sqlSession.getMapper(getM());
			return mapper.batchInsert(tList, getT());
		}
		
	}
	
	public int update (T t) {
		if (t == null) {
			return 0;
		}
		try (SqlSession sqlSession = getSqlSession()) {
			CommonMapper<K,T> mapper = (CommonMapper<K, T>) sqlSession.getMapper(getM());
			return mapper.update(t);
		}
	}
	
	public int delete (K k) {
		if (k == null) {
			return 0;
		}
		try (SqlSession sqlSession = getSqlSession()) {
			CommonMapper<K,T> mapper = (CommonMapper<K, T>) sqlSession.getMapper(getM());
			return mapper.delete(k,getT());
		}
	}
	
	protected SqlSession getSqlSession () {
		return MybatisDB.getSqlSession();
	}
	
	/**
	 * 获取 T的类型
	 * @return
	 */
	private Class<T> getT () {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获取第一个类型参数的真实类型
		return (Class<T>) pt.getActualTypeArguments()[1];
	}
	
	/**
	 * 获取 M的类型
	 * @return
	 */
	private Class<T> getM () {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		// 获取第一个类型参数的真实类型
		return (Class<T>) pt.getActualTypeArguments()[2];
	}
}
