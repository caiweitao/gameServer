package game.core.db.mybatis.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import game.core.db.mybatis.bean.Pram;

/**
 * @author caiweitao
 * @Date 2019年10月11日
 * @Description 
 */
public class SelectSqlProvider extends BaseSqlProvider{

	public String selectById (@Param(value="id")Object id,@Param(value="tc")Class<?> tc) {
		return new SQL(){{
			try {
				String primaryKeyName = getPrimaryKeyName(tc);
				if (primaryKeyName == null) {
					throw new Exception("没有主键，请用注解@ID标记主键，或者将主键名设为id");
				}
				SELECT("*");
				FROM(getTable(tc));
				WHERE(classToTable(primaryKeyName)+"="+id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}}.toString();
	}
	
	public String count (@Param(value="tc")Class<?> tc) {
		return "SELECT COUNT(*) FROM " + getTable(tc);
	}
	
	public String selectListByKey (@Param(value="key")Pram key,@Param(value="tc")Class<?> tc) {
		return new SQL(){{
			try {
				SELECT("*");
				FROM(getTable(tc));
				WHERE(classToTable(key.getFile())+"="+key.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}}.toString();
	}

}
