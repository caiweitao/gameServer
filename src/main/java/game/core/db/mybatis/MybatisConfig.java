package game.core.db.mybatis;
/**
* @author caiweitao
* @Date 2019年10月14日
* @Description 
*/
public class MybatisConfig {

	/**
	 * 初始化Mybatis
	 * @param resource
	 */
	public static boolean init (String resource) {
		return MybatisDB.initSqlSessionFactory(resource);
	}
	
	public static boolean init () {
		return MybatisDB.initSqlSessionFactory();
	}
	
}
