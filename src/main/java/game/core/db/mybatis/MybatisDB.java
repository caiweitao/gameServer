package game.core.db.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author caiweitao
 * @Date 2019年10月11日
 * @Description 
 */
public class MybatisDB {

	private static SqlSessionFactory sqlSessionFactory = null;

	public static boolean initSqlSessionFactory (String resource) {
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean initSqlSessionFactory () {
		String resource = "mybatis-config.xml";//默认配置文件路径
		return initSqlSessionFactory(resource);
	}

	public static SqlSession getSqlSession () {
		if (!checkInit()) System.exit(1);
		return sqlSessionFactory.openSession(true);
	}
	
	public static <T> T getMapper(Class<T> type) {
		try (SqlSession session = getSqlSession()) {
			return session.getMapper(type);
		}
	}
	
	private static boolean checkInit () {
		if (sqlSessionFactory == null) {
			System.err.println("[error] 请调用[MybatisConfig.init(resource)]初始化Mybatis");
			return false;
		}
		return true;
	}
}
