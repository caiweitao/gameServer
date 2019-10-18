package game.core.db.mybatis.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author caiweitao
 * @Date 2019年10月11日
 * @Description 
 */
public class DeleteSqlProvider extends BaseSqlProvider{

	public String delete (@Param(value="id")Object id,@Param(value="tc")Class<?> tc) {
		return new SQL() {{
			try {
				String primaryKeyName = getPrimaryKeyName(tc);
				if (primaryKeyName == null) {
					throw new Exception("没有主键，请用注解@ID标记主键，或者将主键名设为id");
				}
				DELETE_FROM(getTable(tc));
				WHERE(classToTable(primaryKeyName)+"="+id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}}.toString();
	}

}
